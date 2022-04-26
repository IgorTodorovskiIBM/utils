# zOS Open Tools Base
* Clone this directory to begin porting an application to z/OS
* This repo represents a template to begin porting

# External Requirements
* Curl (if specifying a remote url to tar.gz file)
* Gunzip (if specifying a tar.gz file)
* Git (if specifying a git repo)
* Make (if the build tool uses Make to build)

# Steps
* Define the project.json.  
```
{
  "name": "Project Name",
  "path": "path to project
  "envars": {
    "CC": "xlclang",
    "LINK": "xlclang++",
    "script": "setenv.sh"
  },
  "build": {
    "pre-build": "./configure",
    "build": "make",
    "test": "make check",
    "test-failures": "grep failures",
    "install": "make install"
  },
}
```
* name is the project name
* path can be a Git repo, Url to tar.gz, or local tar.gz file
* envars is the set of envars that should be set prior to running the build steps
* build
  * pre-build - run first, typically to configure the Makefile
  * build - runs the build, typically a make
  * test - run the build tests
  * test-failures - captures the number of failures
  * install - installs the build

# Tools
* bin/build.sh - runs the build
* bin/managepatches - Applies patches from the patches directory
