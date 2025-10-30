---
layout: default
title: Setting up a demo configuration
parent: Configuration
nav_order: 4
---

# Setting up a demo configuration

* OpenSearch Security plugin demo configuration
  * == tool /
    * configures security settings / loaded | security index
    * install demo self-signed TLS certificates
    * adds security-related settings | "opensearch.yml"
    * enables you to 
      * 👀replicate a production environment👀
    * | setup EACH supported OpenSearch distribution,
      * AUTOMATICALLY used 
  * == 💡setup of security-related components💡
    * _Example:_ internal users + roles + role mappings + audit configuration + basic authentication + tenants + allow lists 
  * use cases
    * testing purposes

* goal
  * OpenSearch Security plugin demo configuration/ EACH OpenSearch distribution

## Installing the demo configuration

### Docker

* if you want to disable -> [add environment variables](https://github.com/opensearch-project/opensearch-build/tree/main/docker/release#disable-security-plugin-security-dashboards-plugin-security-demo-configurations-and-related-configurations)
  * | OpenSearch
    * `DISABLE_SECURITY_PLUGIN=true`
      * ❌NOT recommended❌
      * Example: `docker run -e "DISABLE_SECURITY_PLUGIN=true" opensearchproject/opensearch:latest`
    * `DISABLE_INSTALL_DEMO_CONFIG=true`
  * | OpenSearch Dashboards,
    * `DISABLE_SECURITY_DASHBOARDS_PLUGIN=true`

### Setting up a custom admin password
**Note**: For OpenSearch versions 2.12 and later, you must set the initial admin password before installation. To customize the admin password, you can take the following steps:

1. Download the following sample [docker-compose.yml](https://github.com/opensearch-project/documentation-website/blob/{{site.opensearch_major_minor_version}}/assets/examples/docker-compose.yml) file.
2. Create a `.env` file.
3. Add the variable `OPENSEARCH_INITIAL_ADMIN_PASSWORD` and set the variable with a strong password. The password must pass the following complexity requirements:

   - Minimum 8 characters
   - Must contain at least one uppercase letter [A--Z]
   - One lowercase letter [a--z]
   - One digit [0--9]
   - One special character

4. Make sure that Docker is running on your local machine
5. Run `docker compose up` from the file directory where your `docker-compose.yml` file and `.env` file are located.

### TAR (Linux) and Mac OS 

For TAR distributions on Linux, download the Linux setup files from the OpenSearch [Download & Get Started](https://opensearch.org/downloads.html) page. Then use the following command to run the demo configuration: 

```bash
./opensearch-tar-install.sh
```
{% include copy.html %}

For OpenSearch 2.12 or later, set a new custom admin password before installation by using the following command:

```bash
export OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>
```
{% include copy.html %}

### Windows

For ZIP distributions on Windows, after downloading and extracting the setup files, run the following command:

```powershell
> .\opensearch-windows-install.bat
```
{% include copy.html %}

For OpenSearch 2.12 or later, set a new custom admin password before installation by running the following command:

```powershell
> set OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password>
```
{% include copy.html %}

### Helm

For Helm charts, the demo configuration is automatically installed during the OpenSearch installation. For OpenSearch 2.12 or later, customize the admin password in `values.yaml` under `extraEnvs`, following the [password requirements]({{site.url}}{{site.baseurl}}/install-and-configure/install-opensearch/docker/#password-requirements):

```yaml
extraEnvs:
  - name: OPENSEARCH_INITIAL_ADMIN_PASSWORD
    value: <custom-admin-password>
```

### RPM

For RPM packages, install OpenSearch and set up the demo configuration by running the following command:

```bash
sudo yum install opensearch-{{site.opensearch_version}}-linux-x64.rpm
```
{% include copy.html %}

For OpenSearch 2.12 or later, set a new custom admin password before installation by using the following command:

```bash
sudo env OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password> yum install opensearch-{{site.opensearch_version}}-linux-x64.rpm
```
{% include copy.html %}

### DEB

For DEB packages, install OpenSearch and set up the demo configuration by running the following command:

```bash
sudo dpkg -i opensearch-{{site.opensearch_version}}-linux-arm64.deb
```
{% include copy.html %}

For OpenSearch 2.12 or later, set a new custom admin password before installation by using the following command:

```bash
sudo env OPENSEARCH_INITIAL_ADMIN_PASSWORD=<custom-admin-password> dpkg -i opensearch-{{site.opensearch_version}}-linux-arm64.deb
```
{% include copy.html %}

## Local distribution

If you are building a local distribution, refer to [DEVELOPER_GUIDE.md](https://github.com/opensearch-project/security/blob/main/DEVELOPER_GUIDE.md) for instructions on building a local binary for the Security plugin.

For OpenSearch 2.12 or later, make sure that you set a strong password before installation.
