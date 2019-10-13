package com.yurich.lastfmapplication.data.database

import com.nhaarman.mockitokotlin2.*
import com.yurich.lastfmapplication.data.database.TestData.testAlbumDetailedInfo
import com.yurich.lastfmapplication.data.database.TestData.testAlbumShortInfo
import com.yurich.lastfmapplication.data.database.TestData.testDatabaseAlbum
import com.yurich.lastfmapplication.data.database.TestData.testDatabaseArtist
import com.yurich.lastfmapplication.data.database.TestData.testDatabaseTrack
import com.yurich.lastfmapplication.data.database.entities.DatabaseAlbumDetailedInfo
import com.yurich.lastfmapplication.data.database.entities.DatabaseAlbumShortInfo
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AlbumsDaoProxyTest {

    @Mock
    lateinit var dao: AlbumsDao

    lateinit var daoProxy: AlbumsDaoProxy

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        daoProxy = AlbumsDaoProxy(dao)
    }

    @Test
    fun getAllAlbums() = runBlocking {
        dao.stub {
            onBlocking { dao.getAlbumsShortInfo() }.doReturn(
                listOf(
                    DatabaseAlbumShortInfo(
                        testDatabaseAlbum,
                        testDatabaseArtist
                    )
                )
            )
        }

        val data = daoProxy.getAllAlbums()

        verify(dao, times(1)).getAlbumsShortInfo()
        assertEquals(1, data.size)

        assertEquals(
            testAlbumShortInfo,
            data.first()
        )
    }

    @Test
    fun getAlbumDetailedInfo() = runBlocking {
        dao.stub {
            onBlocking { dao.getAlbumsDetailedInfo(any()) }.doReturn(
                listOf(
                    DatabaseAlbumDetailedInfo(
                        testDatabaseAlbum,
                        testDatabaseArtist,
                        listOf(testDatabaseTrack)
                    )
                )
            )
        }

        val data = daoProxy.getAlbumDetailedInfo(testAlbumShortInfo)

        verify(dao, times(1)).getAlbumsDetailedInfo("123")
        assertEquals(1, data.size)

        assertEquals(
            testAlbumDetailedInfo,
            data.first()
        )
    }

}