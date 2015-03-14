# Introduction #

UPnP is a set of network protocols that allows networked devices (such as computers, mobile devices, wireless access points, ...) to seamlessly discover and control each other.

UPnP networking consists of several steps. All steps are briefly explained on this page. For detailed information about UPnP, see the UPnP documentation at http://www.upnp.org/


# Step 0 - Addressing #

In this step devices and control points get a network address. Because this step gets done by the underlying operating system, we do not explain it on this page.


# Step 1 - Discovery #

In the discovery step, control points find interesting devices.

How it works:
  * Devices advertise themselves by sending SSDP messages to a specific broadcast address. Devices should periodically send these messages to let the control points know that the device is still available.
  * Control points may search for interesting devices by sending an SSDP search request. This search request may be send as a broadcast message or directly to a known network address. Devices that meet the requirements of the search request must respond with a search response message.

The `com.googlecode.mipnp.upnp.discovery` package contains classes related to the discovery step.


# Step 2 - Description #

In the description step, control points learn more about the capabilities of a device.

How it works:
  * A control point can ask a device for its device description and services descriptions. These descriptions are XML formatted documents that contain information about a device or service. The transfer of these documents is done with HTTP.

The `com.googlecode.mipnp.upnp.description` package contains classes related to the description step.


# Step 3 - Control #

In the control step, control points can ask a service of a device to invoke actions.

How it works:
  * A control point sends a SOAP action to a service of a device. After the action is performed by the device, the device responds with a SOAP response message. This response message can contain the result of the action or an error message.

Services and actions are implemented with JAX-WS.


# Step 4 - Eventing #

This step is not yet implemented in MiPnP. I did not yet found a control point that requires the eventing step to be implemented.


# Step 5 - Presentation #

This step is not yet implemented in MiPnP.