package com.ptbox.osint.service


import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.regex.Pattern

@Service
class TheHarvesterService(private val scanService: ScanService) {
    @Value("\${theharvester.url}")
    private val theHarvesterUrl: String? = null

    @Value("\${theharvester.source}")
    private val source: String? = null

    private val restTemplate = RestTemplate()

    @Throws(Exception::class)
    fun runTheHarvester(domain: String): Map<String?, MutableList<String?>> {
        val organizedData: MutableMap<String?, MutableList<String?>> = HashMap()

        val response = restTemplate.getForObject(
            "$theHarvesterUrl/harvest?domain=$domain&source=$source",
            String::class.java
        )

        if (response != null) {
            for (line in response.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                organizeData(line, organizedData)
            }
        }
        scanService.saveScanData(domain, organizedData)

        return organizedData
    }


    private fun organizeData(line: String, organizedData: MutableMap<String?, MutableList<String?>>) {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val ipPattern = Pattern.compile("\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b")
        val hostPattern = Pattern.compile("(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")

        val emailMatcher = emailPattern.matcher(line)
        val ipMatcher = ipPattern.matcher(line)
        val hostMatcher = hostPattern.matcher(line)

        if (emailMatcher.find()) {
            addToCategory("Emails", emailMatcher.group(), organizedData)
        } else if (ipMatcher.find()) {
            addToCategory("IPs", ipMatcher.group(), organizedData)
        } else if (hostMatcher.find()) {
            addToCategory("Hosts", hostMatcher.group(), organizedData)
        }
    }

    private fun addToCategory(
        category: String,
        value: String,
        organizedData: MutableMap<String?, MutableList<String?>>
    ) {
        organizedData.computeIfAbsent(category) { k: String? -> ArrayList() }.add(value)
    }
}