package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.seed.DataSeeder
import com.example.ui.components.ContentBlock
import com.example.ui.components.parseContentBlocks
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("EduCI", appName)
    }

    @Test
    fun `test database seeding and curriculum queries`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getInstance(context)
        DataSeeder.seedIfNeeded(db)

        val student = db.userDao().getUserByEmail("koffi.jean@educi.ci")
        assertNotNull(student)
        assertEquals("Koffi", student?.firstName)
        assertEquals("4e", student?.className)

        val admin = db.userDao().getUserByEmail("admin@educi.ci")
        assertNotNull(admin)
        assertEquals("admin", admin?.role)
        assertTrue(admin?.isPremium == true)

        val lesson = db.courseDao().getLessonByIdOnce(1L)
        assertNotNull(lesson)
        assertTrue(lesson?.title?.contains("Développement") == true)
    }

    @Test
    fun `test rich content block parser`() {
        val raw = """
            # Titre 1
            ## Sous-titre
            :::definition
            Définition de test
            :::
            §§x^2 + 2x + 1 = 0§§
            | Col 1 | Col 2 |
            | --- | --- |
            | A | B |
            - Puce 1
            1. Numéro 1
        """.trimIndent().replace('§', '$')

        val blocks = parseContentBlocks(raw)
        assertTrue(blocks.any { it is ContentBlock.Heading1 })
        assertTrue(blocks.any { it is ContentBlock.Heading2 })
        assertTrue(blocks.any { it is ContentBlock.Callout })
        assertTrue(blocks.any { it is ContentBlock.MathDisplay })
        assertTrue(blocks.any { it is ContentBlock.Table })
        assertTrue(blocks.any { it is ContentBlock.BulletItem })
        assertTrue(blocks.any { it is ContentBlock.NumberedItem })
    }

    @Test
    fun `test rich content block parser with images, geometric figures and quotes`() {
        val raw = """
            > Ceci est une citation inspirante
            :::figure:triangle
            Triangle rectangle ABC
            Angle droit en A avec hypoténuse BC = 5 cm
            :::
            ![Illustration d'un cours](https://example.com/figure.png|center|large)
        """.trimIndent()

        val blocks = parseContentBlocks(raw)
        assertTrue(blocks.any { it is ContentBlock.Blockquote })
        assertTrue(blocks.any { it is ContentBlock.GeometricFigure })
        assertTrue(blocks.any { it is ContentBlock.ImageBlock })
    }
}
