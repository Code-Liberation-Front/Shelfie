package app.shelfie

import android.app.Application
import app.shelfie.data.AbsRepository
import app.shelfie.data.SettingsStore
import app.shelfie.download.DownloadCenter
import app.shelfie.playlist.PlaylistStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File

class ShelfieApp : Application() {

    /**
     * For work that has to outlive the playback service - notably the final
     * progress save, which is issued while the service is being torn down and
     * would be cancelled along with the service's own scope.
     */
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val settings: SettingsStore by lazy { SettingsStore(this) }
    val repository: AbsRepository by lazy {
        AbsRepository(settings, cacheDir = File(filesDir, "apicache"))
    }
    val downloads: DownloadCenter by lazy { DownloadCenter(this, repository, settings) }
    val playlist: PlaylistStore by lazy { PlaylistStore(this) }
}
