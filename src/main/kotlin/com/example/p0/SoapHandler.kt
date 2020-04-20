package com.example.p0

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.BufferedReader
import java.io.InputStreamReader

class SoapHandler : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        println("Remote address: ${exchange.remoteAddress}")

        println("Request headers")
        val headers = exchange.requestHeaders
        for (key in headers.keys) {
            println("${key} = ${headers.get(key)}")
        }

        when (exchange.requestMethod.toUpperCase()) {
            "POST" -> {
                println("Request Body")
                println(getRequestText(exchange))

                val response: String = fakeSoapResponse(exchange)
                exchange.sendResponseHeaders(200, response.length.toLong())
                exchange.responseHeaders.add("Content-Type", "text/xml")
                exchange.responseBody.use {
                    it.write(response.toByteArray())
                }
            }
            else -> {
                val msg = "405 - Method not allowed"
                println(msg)
                exchange.responseHeaders.add("Content-Type", "text/xml")
                exchange.sendResponseHeaders(405, msg.length.toLong())
                exchange.responseBody.use {
                    it.write(msg.toByteArray())
                }

            }

        }
    }
}

fun fakeSoapResponse(exchange: HttpExchange): String {
    return SEARCH_RESPONSE
}

fun getRequestText(exchange: HttpExchange): String {
    val inputStream = exchange.requestBody
    if (inputStream == null) {
        return "Request body is empty"
    }
    inputStream?.use {
        return BufferedReader(InputStreamReader(it)).readText()
    }
}

const val SEARCH_RESPONSE = """
        <xrsi:receive-money-search-reply xmlns:xrsi="http://www.westernunion.com/schema/xrsi">
    <instant_notification>
        <addl_service_charges />
    </instant_notification>
    <payment_transactions>
        <payment_transaction>
            <sender>
                <name name_type="D">
                    <first_name>DONGMEI</first_name>
                    <middle_name>MID</middle_name>
                    <last_name>DENG</last_name>
                </name>
                <address>
                    <city>BROOKLYN</city>
                    <state>NY</state>
                    <country_code>
                        <iso_code>
                            <country_code>US</country_code>
                            <currency_code>USD</currency_code>
                        </iso_code>
                    </country_code>
                    <state_zip>11226</state_zip>
                    <street>53 WOODS PLACE</street>
                </address>
                <contact_phone>9179716094</contact_phone>
                <mobile_phone>
                    <phone_number />
                </mobile_phone>
                <mobile_details />
            </sender>
            <receiver>
                <name name_type="M">
                    <given_name>HARI</given_name>
                    <paternal_name>PANNEER</paternal_name>
                    <maternal_name>SELVAM</maternal_name>
                </name>
                <address>
                    <country_code>
                        <iso_code>
                            <country_code>PE</country_code>
                            <currency_code>USD</currency_code>
                        </iso_code>
                    </country_code>
                </address>
                <preferred_customer>
                    <account_nbr />
                </preferred_customer>
                <mobile_phone>
                    <phone_number />
                </mobile_phone>
                <mobile_details />
            </receiver>
            <financials>
                <taxes>
                    <tax_worksheet />
                </taxes>
                <gross_total_amount>38000</gross_total_amount>
                <pay_amount>37000</pay_amount>
                <principal_amount>37000</principal_amount>
                <charges>1000</charges>
            </financials>
            <payment_details>
                <expected_payout_location>
                    <state_code />
                    <city />
                </expected_payout_location>
                <destination_country_currency>
                    <iso_code>
                        <country_code>PH</country_code>
                        <currency_code>USD</currency_code>
                    </iso_code>
                </destination_country_currency>
                <originating_country_currency>
                    <iso_code>
                        <country_code>US</country_code>
                        <currency_code>USD</currency_code>
                    </iso_code>
                </originating_country_currency>
                <originating_city>BROOKLYNNY1</originating_city>
                <exchange_rate>1.0000</exchange_rate>
                <original_destination_country_currency>
                    <iso_code>
                        <country_code>CN</country_code>
                        <currency_code>USD</currency_code>
                    </iso_code>
                </original_destination_country_currency>
            </payment_details>
            <filing_date>10-09-15 </filing_date>
            <filing_time>1150A EDT</filing_time>
            <money_transfer_key>2768705819</money_transfer_key>
            <pay_status_description>W/C</pay_status_description>
            <mtcn>2134673020</mtcn>
            <new_mtcn>1528282134673020</new_mtcn>
            <fusion>
                <fusion_status>W/C</fusion_status>
                <account_number />
            </fusion>
            <wu_network_agent_indicator />
        </payment_transaction>
    </payment_transactions>
    <delivery_services>
        <message>
            <message_details context="4">
                <text>one two three four five six seven eight nine ten</text>
                <text>one two three four five six seven eight nine ten</text>
                <text>one two three four five six seven eight nine ten</text>
                <text>one two three four five six seven eight nine ten</text>
            </message_details>
        </message>
    </delivery_services>
    <misc_buffer />
    <foreign_remote_system>
        <identifier>WGBSCNXXXXP</identifier>
        <reference_no>WU15101XXXXXX15</reference_no>
        <counter_id>HH0XXXX6</counter_id>
    </foreign_remote_system>
</xrsi:receive-money-search-reply>
    """