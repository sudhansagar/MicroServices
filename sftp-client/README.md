# SFTP Client Connectivity Tester

This Spring Boot app tests connectivity to an SFTP server using JSch.

## Configure

Update `src/main/resources/application.yml` or provide environment variables:

```yaml
sftp:
  host: sftp.example.com
  port: 22
  username: my-user
  password: my-password
  private-key-path: /path/to/id_rsa
  private-key-passphrase: ""
  timeout-ms: 10000
  strict-host-key-checking: "no"
  protocol: "sftp"
```

Environment variable equivalents use Spring Boot relaxed binding, for example:

```
SFTP_HOST=sftp.example.com
SFTP_PORT=22
SFTP_USERNAME=my-user
SFTP_PASSWORD=my-password
SFTP_PRIVATE_KEY_PATH=/path/to/id_rsa
SFTP_PRIVATE_KEY_PASSPHRASE=
SFTP_TIMEOUT_MS=10000
SFTP_STRICT_HOST_KEY_CHECKING=no
SFTP_PROTOCOL=sftp
```

To test FTP instead of SFTP, set `sftp.protocol=ftp` and use the FTP port (typically 21).

## Run

```bash
mvn spring-boot:run
```

The app will attempt a connection on startup and log success or failure.

## API

Trigger a connectivity test over HTTP:

```bash
curl -X POST http://localhost:8080/api/transfer/test
```
