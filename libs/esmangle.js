#!/usr/bin/env node
/*
  Copyright (C) 2012 Yusuke Suzuki <utatane.tea@gmail.com>
  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
  ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/*jslint node:true */
(function () {
    'use strict';

    var fs = require('fs'),
        path = require('path'),
        root = path.join(path.dirname(fs.realpathSync(__filename)), '..'),
        esprima = require('esprima'),
        escodegen = require('escodegen'),
        estraverse = require('estraverse'),
        esutils = require('esutils'),
        optionator,
        esmangle,
        common,
        argv;

    Error.stackTraceLimit = Infinity;

    esmangle = require(root);
    common = require(path.join(root, 'lib', 'common'));

    optionator = require('optionator')({
        prepend: 'Usage: esmangle file',
        append: 'Version ' + esmangle.version,
        helpStyle: {
            maxPadFactor: 2
        },
        options: [
            {
                option: 'help',
                alias: 'h',
                type: 'Boolean',
                description: 'Show help.',
                restPositional: true
            },
            {
                option: 'source-map',
                type: 'Boolean',
                description: 'Dump source-map.'
            },
            {
                option: 'preserve-completion-value',
                type: 'Boolean',
                description: 'Preserve completion values if needed.'
            },
            {
                option: 'preserve-license-comment',
                type: 'Boolean',
                description: 'Preserve comments with @license, @preserve. But these comment may be lost if attached node is transformed or a comment isn\'t attached to any statement.'
            },
            {
                option: 'non-legacy',
                type: 'Boolean',
                description: 'Drop legacy (<= IE8) browser support.'
            },
            {
                option: 'propagate-license-comment-to-header',
                type: 'Boolean',
                description: 'Preserve comments with @license, @preserve. But these comment may be propagated to the script header.'
            },
            {
                option: 'output',
                alias: 'o',
                type: 'String',
                description: 'Output file.'
            },
            {
                option: 'top-level-context',
                type: 'String',
                default: 'global',
                description: 'Top-level-context of this program is (global|function|module).'
            },
            {
                option: 'in-strict-code',
                type: 'Boolean',
                default: false,
                description: 'Whether the whole program is stric code or not.'
            }
        ]
    });

    argv = optionator.parse(process.argv);

    if (argv.help) {
        console.error(optionator.generateHelp());
        process.exit(0);
    }

    if (argv.preserveLicenseComment && argv.propagateLicenseCommentToHeader) {
        console.error('cannot specify --preserve-license-comment and --propagate-license-comment-to-header both');
        process.exit(1);
    }

    function output(code) {
        if (argv.output) {
            fs.writeFileSync(argv.output, code);
        } else {
            console.log(code);
        }
    }

    function CommentBlock(comment) {
        this.comments = [comment];
    }

    CommentBlock.prototype.append = function (comment) {
        this.comments.push(comment);
    };

    CommentBlock.prototype.isLicense = function () {
        return this.comments.some(function (comment) {
            return /@(?:license|preserve)|copyright/i.test(comment.value);
        });
    };

    function concatSingleLineComments(source, comments) {
        function CommentBuilder(source) {
            this.source = source;
            this.current = null;
            this.result = [];
        }

        CommentBuilder.prototype.append = function (comment) {
            var i, iz, code;

            if (comment.type !== 'Line') {
                this.finishCurrent();
                this.result.push(new CommentBlock(comment));
                return this;
            }

            if (!this.current) {
                this.current = new CommentBlock(comment);
                return this;
            }

            // Both comment and this.current are single line comments.
            for (i = this.current.comments[this.current.comments.length - 1].range[1], iz = comment.range[0]; i < iz; ++i) {
                code = source.charCodeAt(i);
                if (!esutils.code.isLineTerminator(code) && !esutils.code.isWhiteSpace(code)) {
                    // Not contiguous.
                    this.finishCurrent();
                    this.current = new CommentBlock(comment);
                    return this;
                }
            }

            this.current.append(comment);

            return this;
        };

        CommentBuilder.prototype.finishCurrent = function (comment) {
            if (this.current) {
                this.result.push(this.current);
                this.current = null;
            }
        };

        CommentBuilder.prototype.build = function () {
            this.finishCurrent();
            return this.result;
        };

        return comments.reduce(function (builder, comment) {
            return builder.append(comment);
        }, new CommentBuilder()).build();
    }

    function compile(content, filename) {
        var tree, licenses, comments, formatOption, preserveLicenseComment, propagateLicenseComment;

        preserveLicenseComment = argv.preserveLicenseComment;
        propagateLicenseComment = argv.propagateLicenseCommentToHeader;

        tree = esprima.parse(content, {
            loc: true,
            range: true,
            raw: true,
            tokens: true,
            comment: preserveLicenseComment || propagateLicenseComment
        });

        comments = concatSingleLineComments(content, tree.comments || []);

        if (preserveLicenseComment || propagateLicenseComment) {
            licenses = comments.reduce(function (results, commentBlock) {
                if (!commentBlock.isLicense()) {
                    return results;
                }
                return results.concat(commentBlock.comments);
            }, []);
        }

        if (preserveLicenseComment) {
            // Attach comments to the tree.
            estraverse.attachComments(tree, licenses, tree.tokens);
        }

        tree = esmangle.optimize(tree, null, {
            destructive: true,
            directive: true,
            preserveCompletionValue: argv.preserveCompletionValue,
            legacy: !argv.nonLegacy,
            topLevelContext: argv.topLevelContext,
            inStrictCode: argv.inStrictCode
        });
        tree = esmangle.mangle(tree, {
            destructive: true,
            distinguishFunctionExpressionScope: false
        });

        if (propagateLicenseComment) {
            tree.leadingComments = licenses;
        }

        formatOption = common.deepCopy(escodegen.FORMAT_MINIFY);
        formatOption.indent.adjustMultilineComment = true;

        return escodegen.generate(tree, {
            format: formatOption,
            sourceMap: argv.sourceMap && filename,
            directive: true,
            comment: preserveLicenseComment || propagateLicenseComment
        });
    }

    if (argv._.length === 0) {
        // no file is specified, so use stdin as input
        (function () {
            var code = '';
            process.stdin.on('data', function (data) {
                code += data;
            });
            process.stdin.on('end', function (err) {
                output(compile(code, 'stdin'));
            });
            process.stdin.resume();
        }());
    } else {
        argv._.forEach(function (filename) {
            var content, result;
            content = fs.readFileSync(filename, 'utf-8');
            result = compile(content, filename);
            output(result);
        });
    }
}());
/* vim: set sw=4 ts=4 et tw=80 : */