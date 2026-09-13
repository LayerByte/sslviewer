# Sslviewer

Certificate information viewer.

School Purpose Only.

Only use this project on systems and networks you own or have explicit permission to test.

## Overview

Sslviewer is a defensive cybersecurity and system-administration project written in Modern Java. It focuses on read-only analysis, validation, clear output, and safe handling of user-provided files, URLs, hosts, or text.

## Features

- Defensive, educational workflow
- Read-only file and network analysis where applicable
- Input validation and friendly error messages
- No credential collection, payload delivery, exploitation, or destructive actions
- Small, understandable codebase suitable for students

## Requirements

- Java 21+
- Maven

## Installation

Clone or copy this project folder, then follow the standard build or run command for Modern Java.

## Usage

`mvn package`
`java -cp target/classes com.layerbyte.sslviewer.App file sample.log`

## Example

```text
java -cp target/classes com.layerbyte.sslviewer.App net
```

## How It Works

The tool validates input, performs a focused defensive analysis task, and prints a clear report. File operations are read-only and network operations use normal platform TLS and timeout behavior.

## Security Considerations

- Do not run tools against systems without permission.
- Do not paste secrets into command-line arguments.
- Review output before sharing it because file paths and hostnames may be sensitive.
- Network tools do not disable certificate validation.

## Limitations

- Designed for education and local administration workflows.
- Not a replacement for enterprise security monitoring.
- Some platform-specific data may vary by operating system.

## Disclaimer

This project is for defensive learning, school assignments, and authorized administration. It does not include malware, credential theft, brute-force attacks, exploitation, payload delivery, persistence, or bypass functionality.

## License

Released under the MIT License.
