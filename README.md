# Live Translation App

An app the can be translated from a remote server

The app uses the existing android string resource keys as basis for the translation.

Text views are intercepted and swapped while the view renders if a resource translation has been matched. 

## Getting Started


### Configuration

The host and port of the Translation Rest Api

com.wfdb.livetranslation.config.Config.java

```
DEFAULT_HOST = "127.0.0.1";
DEFAULT_PORT = "8080";
```

### Building the project

building the binaries 

```
./gradlew build
```

Or

- import the project on android studio.
- build and install on an emulator or on a device

### Running Tests

```
./gradlew test
```

### Author

* **Warren Balcos** - *Initial work* - [warrenbalcos](https://github.com/warrenbalcos)

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

