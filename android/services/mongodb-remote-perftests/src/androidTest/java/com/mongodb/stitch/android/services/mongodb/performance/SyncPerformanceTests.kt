package com.mongodb.stitch.android.services.mongodb.performance

import android.support.test.runner.AndroidJUnit4

import com.google.android.gms.tasks.Tasks

import org.bson.types.Binary
import org.bson.Document
import org.bson.types.ObjectId
import org.junit.After

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SyncPerformanceTests {
    private val outputToStitch = false

    companion object {
        private val runId by lazy {
            ObjectId()
        }
    }

    private var testHarness = SyncPerformanceIntTestsHarness()

    @Before
    fun setup() {
        this.testHarness.setup()
    }

    @After
    fun teardown() {
        this.testHarness.teardown()
    }

    // Tests for L2R-only scenarios

    @Test
    fun testL2ROnlyInitialSync() {
        SyncL2ROnlyPerformanceTestDefinitions.testInitialSync(this.testHarness, runId)
    }

    @Test
    fun testL2ROnlyDisconnectReconnect() {
        SyncL2ROnlyPerformanceTestDefinitions.testDisconnectReconnect(this.testHarness, runId)
    }

    @Test
    fun testL2ROnlySyncPass() {
        SyncL2ROnlyPerformanceTestDefinitions.testSyncPass(this.testHarness, runId)
    }

    // Placeholder tests until the whole performance testing suite is in place

    @Test
    fun testInitialSync() {
        val testParams = TestParams(
            runId = runId,
            testName = "testInitialSync",
            dataProbeGranularityMs = 400L,
            docSizes = intArrayOf(50),
            numDocs = intArrayOf(10),
            numIters = SyncPerformanceTestUtils.getConfiguredIters(),
            numOutliersEachSide = 0,
            outputToStitch = outputToStitch,
            stitchHostName = SyncPerformanceTestUtils.getConfiguredStitchHostname()
        )

        testHarness.runPerformanceTestWithParams(testParams, { ctx, docSize, numDocs ->
            val documents = getDocuments(numDocs, docSize)
            assertEquals(0L, Tasks.await(ctx.testColl.count()))
            Tasks.await(ctx.testColl.insertMany(documents))
            assertEquals(numDocs.toLong(), Tasks.await(ctx.testColl.count()))
        })
    }

    @Test
    fun testDisconnectReconnect() {
        val testParams = TestParams(
            runId = runId,
            testName = "testDisconnectReconnect",
            dataProbeGranularityMs = 400L,
            docSizes = intArrayOf(30, 60),
            numDocs = intArrayOf(10),
            numIters = SyncPerformanceTestUtils.getConfiguredIters(),
            numOutliersEachSide = 0,
            outputToStitch = outputToStitch,
            stitchHostName = SyncPerformanceTestUtils.getConfiguredStitchHostname()
        )

        testHarness.runPerformanceTestWithParams(testParams, { ctx, docSize, numDocs ->
            val documents = getDocuments(numDocs, docSize)
            assertEquals(Tasks.await(ctx.testColl.count()), 0L)
            Tasks.await(ctx.testColl.insertMany(documents))
            assertEquals(Tasks.await(ctx.testColl.count()), numDocs.toLong())
        })
    }

    private fun getDocuments(
        numberOfDocs: Int,
        sizeOfDocsInBytes: Int
    ): List<Document>? {
        val array: List<Byte> = (0 until sizeOfDocsInBytes).map { 0.toByte() }
        return (0 until numberOfDocs).map {
            Document(mapOf(
                    "_id" to ObjectId(),
                    "bin" to Binary(array.toByteArray())
            ))
        }
    }
}
