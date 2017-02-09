module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON("package.json"),
        "generate-manifest": {
            name: "<%= pkg.name %>",
            version: "<%= pkg.version %>",
            manifestDir: grunt.option("target")
        },
        "copy-file": {
            source: grunt.option("source"),
            target: grunt.option("target")
        },
        "symlink": {
          options: {
            overwrite: true
          },
          explicit: {
            src: grunt.option("source"),
            dest: grunt.option("target")
          }
        }
    });

    grunt.loadNpmTasks("grunt-contrib-symlink");

    grunt.registerTask("generate-manifest", "Generate the Electron manifest.", function() {
        grunt.config.requires("generate-manifest.name");
        grunt.config.requires("generate-manifest.version");
        grunt.config.requires("generate-manifest.manifestDir");

        var config = grunt.config("generate-manifest");
        var json = {
            name: config.name,
            version: config.version,
            main: "./main.js"
        };

        var manifestFile = config.manifestDir + "/package.json";
        grunt.file.write(manifestFile, JSON.stringify(json, null, 2));
    });

    grunt.registerTask("copy-file", "Copies a given file to a new location.", function() {
        grunt.config.requires("copy-file.source");
        grunt.config.requires("copy-file.target");

        var config = grunt.config("copy-file");
        grunt.file.copy(config.source, config.target);
    });
};
