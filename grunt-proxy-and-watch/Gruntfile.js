module.exports = function(grunt) {

    var path = require('path');
    var url = require('url');

    grunt.initConfig({
            pkg: grunt.file.readJSON('package.json'),
            watch: {
                options: {
                    livereload: 35729
                },
                html: {
                    files: ['*.html'],
                    tasks: ['build']
                },
            },
            connect: {
                server: {
                    options: {
                        port: 9000,
                        keepalive: false,
                        livereload: true,

                        middleware: function(connect, options, middlewares) {
                            var _staticPath = path.resolve(__dirname);
                            return [
                                    require('connect-logger')(),
                                    require('connect-livereload')({ //inject livereload script into htmlt files
                                        port: 35729
                                    }),
                                    require('proxy-middleware')({
                                            protocol: 'http:',
                                            hostname: 'localhost',
                                            port: '8080',
                                            pathname: '/other-web-server/rest',
                                            route: '/rest'// the entry point of the proxy
                                    }),
                                require('serve-static')(_staticPath), // Serve static files.
                                require('serve-index')(_staticPath) // Make empty directories browse-able.
                        ];
                    }
                }
            }
        }
    });

    // Load grunt tasks automatically
    require('load-grunt-tasks')(grunt);

    grunt.registerTask('serve', function(target) {
        grunt.task.run(['connect:server', 'watch']);
    });

    grunt.registerTask('build', function(target) {
        grunt.log.write("Should rebuild app!");
    });

};