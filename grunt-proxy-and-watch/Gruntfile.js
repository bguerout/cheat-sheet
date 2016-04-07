module.exports = function (grunt) {

    var path = require('path');
    var url = require('url');

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        commonDependencies: ['spec.js'],
        dependencies: ['server.js', '*.js'],
        watch: {
            options: {
                livereload: 35729
            },
            html: {
                files: ['**/*.js'],
                tasks: ['build']
            }
        },
        connect: {
            server: {
                options: {
                    port: 9000,
                    keepalive: false,
                    livereload: true,

                    middleware: function (connect, options, middlewares) {
                        var _staticPath = path.resolve(__dirname);
                        return [
                            require('proxy-middleware')({
                                protocol: 'http:',
                                hostname: 'localhost',
                                port: '8080',
                                pathname: '/rest',
                                route: '/rest-proxy'// the entry point of the proxy
                            }),
                            require('serve-static')(_staticPath), // Serve static files.
                            require('serve-index')(_staticPath) // Make empty directories browse-able.
                        ];
                    }
                }
            }
        },
        protractor: {
            options: {
                configFile: "conf.js", // Default config file
                keepAlive: true, // If false, the grunt process stops when the test fails.
                noColor: false, // If true, protractor will not use colors in its output.
                args: {}
            },
            e2e: {}
        },
        includeSource: {
            options: {
                basePath: '',
                baseUrl: 'public/'
            },
            all: {
                files: [{
                    expand: true,     // Enable dynamic expansion.
                    cwd: '',      // Src matches are relative to this path.
                    src: ['*.html'], // Actual pattern(s) to match.
                    dest: 'dist/'

                }]
            }
        }
    });

    // Load grunt tasks automatically
    require('load-grunt-tasks')(grunt);

    grunt.registerTask('serve', function (target) {
        grunt.task.run(['connect:server', 'watch']);
    });

    grunt.registerTask('serve', function (target) {
        grunt.task.run(['connect:server', 'watch']);
    });

    grunt.registerTask('build', function (target) {
        grunt.task.run(['includeSource']);
    });

};