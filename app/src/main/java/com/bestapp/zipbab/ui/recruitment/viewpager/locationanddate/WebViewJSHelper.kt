package com.bestapp.zipbab.ui.recruitment.viewpager.locationanddate


/**
 * WebView와 통신할 때 사용하는 abstract class
 *
 * 참고자료 : https://tempodivalse.tistory.com/8
 */
class WebViewJSHelper(
    private val onResult: (String, String) -> Unit,
) {
    @android.webkit.JavascriptInterface
    fun result(address: String, zipCode: String) {
        onResult(address, zipCode)
    }
    
    companion object {
        const val PROTOCOL_NAME = "address_bridge"
    }
}