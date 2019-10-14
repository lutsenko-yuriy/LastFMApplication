package com.yurich.lastfmapplication.data.network

import com.nhaarman.mockitokotlin2.*
import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.domain.status.Either
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LastFMServiceAdapterTest {

    @Mock
    private lateinit var service: LastFMService

    @Mock
    private lateinit var adapter: LastFMServiceAdapter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        adapter = LastFMServiceAdapter("apiKey", service)
    }

    private val mockArtist: ArtistShortInfo by lazy {
        ArtistShortInfo(
            "Cher",
            "https://www.last.fm/music/Cher",
            ArtistShortInfo.Images(
                "https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png",
                "https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png"
            )
        )
    }

    private val mediaType by lazy {
        MediaType.get("application/json")
    }

    private val mockAlbum: AlbumShortInfo by lazy {
        AlbumShortInfo(
            "Believe",
            "https://www.last.fm/music/Cher/Believe",
            AlbumShortInfo.Images(
                "https://lastfm.freetls.fastly.net/i/u/64s/3b54885952161aaea4ce2965b2db1638.png",
                "https://lastfm.freetls.fastly.net/i/u/174s/3b54885952161aaea4ce2965b2db1638.png"
            ),
            mockArtist
        )
    }

    private val mockTrack by lazy {
        AlbumDetailedInfo.Track("https://www.last.fm/music/Cher/_/Believe", "238", "Believe")
    }

    @Test
    fun `getArtistsByQuery returns artists`() = runBlocking {
        service.stub {
            onBlocking { service.getData(any()) }.doReturn(
                ResponseBody.create(
                    mediaType, "{\n" +
                            "  \"results\": {\n" +
                            "    \"opensearch:Query\": {\n" +
                            "      \"#text\": \"\",\n" +
                            "      \"role\": \"request\",\n" +
                            "      \"searchTerms\": \"cher\",\n" +
                            "      \"startPage\": \"1\"\n" +
                            "    },\n" +
                            "    \"opensearch:totalResults\": \"62667\",\n" +
                            "    \"opensearch:startIndex\": \"0\",\n" +
                            "    \"opensearch:itemsPerPage\": \"30\",\n" +
                            "    \"artistmatches\": {\n" +
                            "      \"artist\": [\n" +
                            "        {\n" +
                            "\"name\": \"Cher\",\n" +
                            "          \"listeners\": \"1140123\",\n" +
                            "          \"mbid\": \"bfcc6d75-a6a5-4bc6-8282-47aec8531818\",\n" +
                            "          \"url\": \"https://www.last.fm/music/Cher\",\n" +
                            "          \"streamable\": \"0\",\n" +
                            "          \"image\": [\n" +
                            "            {\n" +
                            "              \"#text\": \"https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                            "              \"size\": \"small\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "              \"#text\": \"https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                            "              \"size\": \"medium\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "              \"#text\": \"https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                            "              \"size\": \"large\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "              \"#text\": \"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                            "              \"size\": \"extralarge\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "              \"#text\": \"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\n" +
                            "              \"size\": \"mega\"\n" +
                            "            }\n" +
                            "          ]\n" +
                            "        }\n]\n" +
                            "    },\n" +
                            "    \"@attr\": {\n" +
                            "      \"for\": \"cher\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}"
                )
            )
        }

        val artists = adapter.getArtistsByQuery(" ")

        verify(service, times(1)).getData(any())
        assertThat(artists, IsInstanceOf(Either.Result::class.java))

        val listOfArtists = (artists as Either.Result).result

        assertEquals(1, listOfArtists.size)
        assertEquals(mockArtist, listOfArtists.first())
    }

    // A web service with a single endpoint which is responsible for EVERYTHING is not good
    // TODO - talk about LastFM API Delegate on a backend side or improving LastFM API itself
    @Test
    fun `any of these methods returns error`() = runBlocking {
        service.stub {
            onBlocking { service.getData(any()) }.doReturn(
                ResponseBody.create(
                    mediaType,
                    "{\n" +
                            "  \"error\": 10,\n" +
                            "  \"message\": \"Invalid API key - You must be granted a valid key by last.fm\"\n" +
                            "}"
                )
            )
        }

        val artists = adapter.getArtistsByQuery(" ")

        assertThat(artists, IsInstanceOf(Either.Error::class.java))
    }


    @Test
    fun `getAlbumsByArtist returns albums`() = runBlocking {
        service.stub {
            onBlocking { service.getData(any()) }.doReturn(
                ResponseBody.create(
                    mediaType,
                    "{\n" +
                            "\"topalbums\": {\n" +
                            "\"album\": [\n" +
                            "{\n" +
                            "\"name\": \"Believe\",\n" +
                            "\"playcount\": 2546627,\n" +
                            "\"mbid\": \"63b3a8ca-26f2-4e2b-b867-647a6ec2bebd\",\n" +
                            "\"url\": \"https://www.last.fm/music/Cher/Believe\",\n" +
                            "\"artist\": {\n" +
                            "\"name\": \"Cher\",\n" +
                            "\"mbid\": \"bfcc6d75-a6a5-4bc6-8282-47aec8531818\",\n" +
                            "\"url\": \"https://www.last.fm/music/Cher\"\n" +
                            "},\n" +
                            "\"image\": [\n" +
                            "{\n" +
                            "\"#text\": \"https://lastfm.freetls.fastly.net/i/u/34s/3b54885952161aaea4ce2965b2db1638.png\",\n" +
                            "\"size\": \"small\"\n" +
                            "},\n" +
                            "{\n" +
                            "\"#text\": \"https://lastfm.freetls.fastly.net/i/u/64s/3b54885952161aaea4ce2965b2db1638.png\",\n" +
                            "\"size\": \"medium\"\n" +
                            "},\n" +
                            "{\n" +
                            "\"#text\": \"https://lastfm.freetls.fastly.net/i/u/174s/3b54885952161aaea4ce2965b2db1638.png\",\n" +
                            "\"size\": \"large\"\n" +
                            "},\n" +
                            "{\n" +
                            "\"#text\": \"https://lastfm.freetls.fastly.net/i/u/300x300/3b54885952161aaea4ce2965b2db1638.png\",\n" +
                            "\"size\": \"extralarge\"\n" +
                            "}\n" +
                            "]\n" +
                            "}\n" +
                            "],\n" +
                            "\"@attr\": {\n" +
                            "\"artist\": \"Cher\",\n" +
                            "\"page\": \"1\",\n" +
                            "\"perPage\": \"50\",\n" +
                            "\"totalPages\": \"705\",\n" +
                            "\"total\": \"35243\"\n" +
                            "}\n" +
                            "}\n" +
                            "}"
                )
            )
        }

        val albums = adapter.getAlbumsByArtist(mockArtist)

        verify(service, times(1)).getData(any())

        assertThat(albums, IsInstanceOf(Either.Result::class.java))

        val listOfAlbums = (albums as Either.Result).result

        assertEquals(1, listOfAlbums.size)
        assertEquals(mockAlbum, listOfAlbums.first())
    }

    @Test
    fun `getTracksByAlbum returns tracks`() = runBlocking {
        service.stub {
            onBlocking { service.getData(any()) }.doReturn(
                ResponseBody.create(
                    mediaType,
                    """
                        {
album: {
name: "Believe",
artist: "Cher",
mbid: "63b3a8ca-26f2-4e2b-b867-647a6ec2bebd",
url: "https://www.last.fm/music/Cher/Believe",
tracks: {
track: [
{
name: "Believe",
url: "https://www.last.fm/music/Cher/_/Believe",
duration: "238",
artist: {
name: "Cher",
mbid: "bfcc6d75-a6a5-4bc6-8282-47aec8531818",
url: "https://www.last.fm/music/Cher"
}
}
]
}
}
}
                    """.trimIndent()
                )
            )
        }

        val tracks = adapter.getAlbumDetailedInfo(mockAlbum)

        verify(service, times(1)).getData(any())

        assertThat(tracks, IsInstanceOf(Either.Result::class.java))

        val listOfTracks = (tracks as Either.Result).result

        assertEquals(mockAlbum, listOfTracks.shortInfo)

        assertEquals(1, listOfTracks.tracks.size)
        assertEquals(mockTrack, listOfTracks.tracks.first())
    }
}