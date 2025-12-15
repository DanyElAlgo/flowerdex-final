package com.example.flowerdexapp.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "TU_URL_DE_SUPABASE",
        supabaseKey = "TU_ANON_KEY_DE_SUPABASE"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)

        // Configuración para que entienda tus Enums y snake_case de SQL
        defaultSerializer = KotlinXSerializer(Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }
}