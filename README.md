# Wifi_Configrations
 By using this code you can connect to wifi using ssid and password
 ![App example showing UI of wifi.](screenshot1.png)

Here's a breakdown of what each part does:

1. Check Wi-Fi Enabled:
Checks if Wi-Fi is enabled on the device. If not, it shows a Toast message to the user and opens the Wi-Fi settings panel.

2. Create WifiNetworkSuggestion:
3. Creates a WifiNetworkSuggestion object with the specified SSID (network name) and passphrase.
   This suggestion is used to provide information about the network you want to connect to.
4. Remove and Add Network Suggestions: 
    Removes any existing network suggestions for the specified network and adds the new suggestion.
5. BroadcastReceiver:
   Sets up a BroadcastReceiver to listen for the ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION broadcast. 
   This broadcast is sent when the device successfully connects to a Wi-Fi network suggested by the app.

6. hasScanned Flag:
   Sets a flag (hasScanned) to false, indicating that the network has not been scanned yet. 
    The purpose of this flag might be related to tracking whether a network scan has been performed.

This code is specifically designed for Android devices running Android 10 (Q) and above, 
where the new Wi-Fi suggestion API is available for improved network connection handling.

