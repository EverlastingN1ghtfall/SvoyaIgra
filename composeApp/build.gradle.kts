import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    id("org.jetbrains.dokka") version "1.9.20"
}

kotlin {
    jvmToolchain(17)
    jvm() {
        withJava()
    }
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.example.svoyaigra.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.svoyaigra"
            packageVersion = "1.0.0"
        }
    }
}

tasks.dokkaHtml {
    dokkaSourceSets.configureEach {
        // укажем KMP sourceSet, чтобы точно был jvmMain
        perPackageOption {
            matchingRegex.set("org.example.svoyaigra.*")
            suppress.set(false)
        }
    }
    outputDirectory.set(buildDir.resolve("dokka"))
}
