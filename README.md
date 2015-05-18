# openchemlib-js

JavaScript interface with the [openchemlib](https://github.com/actelion/openchemlib) java library

## [Documentation](./Documentation.md)

## Development

To build this project, you need :
* java >= 1.7
* GWT 2.7 ([link](http://www.gwtproject.org/versions.html))
* Node.js 0.12 or io.js ([link](https://iojs.org/dist/latest/))

### Install dependencies

`npm install`
`[sudo] npm install -g gulp`

### Configure directories

Copy `config.default.json` to `config.json` and put the path to the GWT classes on your computer.  
If you want to update the java code from openchemlib, provide the path to the openchemlib source.

### Gulp tasks

__Options__
* -m [name]: only process module "name"
* -v: verbose output

#### compile:min / compile:pretty

Execute the GWT compilator

#### export

Transform the GWT compiled files to javascript modules

#### build:min / build:pretty

Compile and export

#### copy:openchemlib

Copy the required java files from the openchemlib project

## License

BSD

