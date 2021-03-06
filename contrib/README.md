#### Contribution Guide

### Summary

This project follows [Semantic Versioning 2.0](https://semver.org/). In general, every release is associated with a tag and a changelog. `master` serves as the mainline branch for the project and represent the latest state of development.

### Publishing a New SDK version
```bash
# run bump_version.bash with either patch, minor, or major
./bump_version.bash <snapshot|beta|patch|minor|major>

# send an email detailing the changes to the https://groups.google.com/d/forum/mongodb-stitch-announce mailing list
```

#### Configuring Bintray Upload
For the `./gradlew bintrayUpload` command to work properly, you must locally specify your Bintray credentials. You can do this by adding the following lines to your `local.properties` file:

```
publish.bintray.user=<bintray_user>
publish.bintray.apiKey=<bintray_api_key>
publish.bintray.gpgPassphrase=<gpg_passphrase># optional
publish.bintray.mavenSyncUser=<maven_central_sync_user> # optional
publish.bintray.mavenSyncPassword=<maven_central_sync_password> # optional
```

### Snapshot Versions

A snapshot is a Java specific packaging pattern that allows us to publish the unstable state of a version. The general publishing flow can be followed using `snapshot` as the bump type in `bump_version`. This will not do any tagging but will only publish the artifacts and docs.

### Patch Versions

The work for a patch version should happen on a "support branch" (e.g. 1.2.x). The purpose of this is to be able to support a minor release while excluding changes from the mainstream (`master`) branch. If the changes in the patch are relevant to other branches, including `master`, they should be backported there. The general publishing flow can be followed using `patch` as the bump type in `bump_version`.

### Minor Versions

The general publishing flow can be followed using `minor` as the bump type in `bump_version`.

### Major Versions

The general publishing flow can be followed using `major` as the bump type in `bump_version`. In addition to this, the release on GitHub should be edited for a more readable format of key changes and include any migration steps needed to go from the last major version to this one.

### Testing (MongoDB Internal Contributors Only)

* Before committing, the ```connectedDebugAndroidTest``` suite of integration tests must succeed.
* The tests require the following setup:
    * You must enable clear text traffic in the core Android application locally (**do not commit this change**)
        * In file *android/core/src/main/AndroidManifest.xml*, change the ```application``` XML tag as follows:
            ```<application android:usesCleartextTraffic="true">```
    * You must run at least one ```mongod``` instance with replica sets initiated or a ```mongos``` instance with same locally on port 26000
    * You must run the Stitch server locally using the Android-specific configuration:
        ```--configFile ./etc/configs/test_config_sdk_base.json --configFile ./etc/configs/test_config_sdk_android.json```
    * For example, here's how to start mongod (using mlaunch), start the Stitch server, then run the tests:
```
mlaunch init --replicaset --port 26000

# in stitch source directory
go run cmd/server/main.go --configFile ./etc/configs/test_config_sdk_base.json --configFile ./etc/configs/test_config_sdk_android.json

# in android SDK directory
./gradlew connectedDebugAndroidTest
```
