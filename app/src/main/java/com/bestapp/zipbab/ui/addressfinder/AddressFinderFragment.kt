package com.bestapp.zipbab.ui.addressfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import com.bestapp.zipbab.databinding.FragmentAddressFinderBinding
import com.bestapp.zipbab.ui.recruitment.viewpager.locationanddate.WebViewJSHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressFinderFragment : Fragment() {

    private var _binding: FragmentAddressFinderBinding? = null
    private val binding: FragmentAddressFinderBinding
        get() = _binding!!

    private val assetLoader: WebViewAssetLoader by lazy {
        WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(requireContext()))
            .setDomain(DOMAIN)
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressFinderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setWebView()
    }

    private fun setListener() {
        binding.mt.setNavigationOnClickListener {
            if (!findNavController().popBackStack()) {
                requireActivity().finish()
            }
        }
    }

    private fun setWebView() {
        binding.wv.addJavascriptInterface(WebViewJSHelper { address, zipCode ->
            lifecycleScope.launch(Dispatchers.Main) {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    ADDRESS_RESULT_KEY,
                    AddressInfo(address, zipCode)
                )

                if (!findNavController().popBackStack()) {
                    requireActivity().finish()
                }
            }
        }, WebViewJSHelper.PROTOCOL_NAME)

        binding.wv.webViewClient =
            object : WebViewClientCompat() {
                override fun shouldInterceptRequest(
                    view: WebView,
                    request: WebResourceRequest
                ): WebResourceResponse? {
                    return assetLoader.shouldInterceptRequest(request.url)
                }
            }

        with(binding.wv.settings) {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = false
            allowContentAccess = false
        }

        binding.wv.loadUrl(ADDRESS_LOCATION)
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    companion object {
        private const val DOMAIN = "address.finder.net"
        private const val ADDRESS_LOCATION = "https://${DOMAIN}/assets/html/daum_address.html"
        const val ADDRESS_RESULT_KEY = "ADDRESS_RESULT_KEY"
    }
}