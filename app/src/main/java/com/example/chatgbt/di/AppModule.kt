package com.example.chatgbt.di

import com.example.chatgbt.api.OpenAiApi
import com.example.chatgbt.models.ChatRequest
import com.example.chatgbt.models.Message
import com.example.chatgbt.repository.OpenAiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer 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")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    @Named("userMessageContent")
    fun provideUserMessageContent(): String {
        // Replace this with the code to obtain the user message content dynamically
        return ""
    }

    @Provides
    @Singleton
    fun provideChatRequest(@Named("userMessageContent") userMessageContent: String): ChatRequest{
        return ChatRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                Message(role = "user" , content = userMessageContent)
            )
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/v1/chat/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenAiApi(retrofit: Retrofit): OpenAiApi {
        return retrofit.create(OpenAiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(openAiApi: OpenAiApi): OpenAiRepository {
        return OpenAiRepository(openAiApi)
    }

}