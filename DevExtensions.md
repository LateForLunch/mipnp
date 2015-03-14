# Introduction #

Extensions allow developers to add functionality to MiPnP without modifying the existing code. This page briefly explains what to keep in mind when creating an extension.


# Creating an extension #

Keep in mind that
  * you can only create extensions to add media sources (for now).
  * you implement the `com.googlecode.mipnp.mediaserver.library.MediaSource` interface.
  * you use the `com.googlecode.mipnp.extension.ExtensionInfo` annotation to specify a name and description for the extension.
  * when creating a jar file, you add a file named `com.googlecode.mipnp.mediaserver.library.MediaSource` in the `/META-INF/services/` directory in the jar file. This file should contain a list of fully-qualified binary names of the classes that implement the `com.googlecode.mipnp.mediaserver.library.MediaSource` interface, one per line.

For more information about the `/META-INF/services/` directory, see http://download.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html