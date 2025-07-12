package com.example.webshoptest.ui.screens.offers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.webshoptest.domain.model.InsuranceOffer
import com.example.webshoptest.ui.components.CustomSnackbar
import com.example.webshoptest.ui.screens.data.DataViewModel

@Composable
fun OffersScreen(
    modifier: Modifier = Modifier,
    viewModel: DataViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                CustomSnackbar(data.visuals.message)
            }
        }
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = Color(0xFF108565))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
            ) {
                Log.d("OFFERS FETCHED", uiState.offers.size.toString())
                items(uiState.offers) { offer ->
                    InsuranceOfferCard(offer = offer)
                }
            }
        }
    }
}

@Composable
fun InsuranceOfferCard(offer: InsuranceOffer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Variant: ${offer.productVariant}",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Insured Amount: €${offer.amountInsured}",
                color = Color.Black
            )
            Text(
                text = "Premium: €${offer.premiumEur} | ${offer.premiumRsd} RSD",
                color = Color.Black
            )

            if (offer.cancelTrip) {
                Text(
                    text = "Cancellation Included",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Cancellation Premium: €${offer.cancellationPremiumEur} | ${offer.cancellationPremiumRsd} RSD",
                    color = Color.Black
                )
            } else {
                Text(
                    text = "No Trip Cancellation",
                    color = Color(0xFFB71C1C),
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (offer.pandemicProtectionIncluded) {
                Text(
                    text = "Pandemic Protection: ✅",
                    color = Color.Black)
            } else {
                Text(
                    text = "Pandemic Protection: ❌",
                    color = Color.Black)
            }
        }
    }
}
