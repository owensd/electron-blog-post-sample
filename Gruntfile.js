module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON("package.json"),
        "generate-manifest": {
            name: "<%= pkg.name %>",
            version: "<%= pkg.version %>",
            main: "<%= pkg.config.electron.main %>",
            manifestDir: "<%= pkg.config.electron.manifestDir %>"
        },
        "generate-mainjs": {
            main: "<%= pkg.config.electron.main %>",
            manifestDir: "<%= pkg.config.electron.manifestDir %>"
        },
        "symlink": {
          options: {
            overwrite: true
          },
          explicit: {
            src: "<%= pkg.config.symlink.src %>",
            dest: "<%= pkg.config.symlink.dest %>"
          }
        }
    });

    grunt.loadNpmTasks("grunt-contrib-symlink");

    grunt.registerTask("generate-manifest", "Generate the Electron manifest.", function() {
        grunt.config.requires("generate-manifest.name");
        grunt.config.requires("generate-manifest.version");
        grunt.config.requires("generate-manifest.main");
        grunt.config.requires("generate-manifest.manifestDir");

        var config = grunt.config("generate-manifest");
        var json = {
            name: config.name,
            version: config.version,
            main: config.main
        };

        var manifestFile = config.manifestDir + "/package.json";
        grunt.file.write(manifestFile, JSON.stringify(json, null, 2));
    });

    grunt.registerTask("generate-mainjs", "Generate the Electron main.js file.", function() {
        grunt.config.requires("generate-mainjs.main");
        grunt.config.requires("generate-mainjs.manifestDir");

        var config = grunt.config("generate-mainjs");
        var content = "require('./" + config.main + "');\n";

        var manifestFile = config.manifestDir + "/main.js";
        grunt.file.write(manifestFile, content);
    });
};
