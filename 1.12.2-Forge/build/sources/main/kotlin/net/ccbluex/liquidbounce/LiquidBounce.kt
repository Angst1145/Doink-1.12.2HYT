package net.ccbluex.liquidbounce

import tomk.utils.TipSoundManager
import net.ccbluex.liquidbounce.api.Wrapper
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation
import net.ccbluex.liquidbounce.event.ClientShutdownEvent
import net.ccbluex.liquidbounce.event.EventManager
import net.ccbluex.liquidbounce.features.command.CommandManager
import net.ccbluex.liquidbounce.features.module.ModuleManager
import net.ccbluex.liquidbounce.features.special.AntiForge
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof
import net.ccbluex.liquidbounce.file.FileManager
import net.ccbluex.liquidbounce.management.CombatManager
import net.ccbluex.liquidbounce.management.MemoryManager
import net.ccbluex.liquidbounce.obfuscation.NativeMethod
import net.ccbluex.liquidbounce.script.ScriptManager
import net.ccbluex.liquidbounce.script.remapper.Remapper.loadSrg
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui
import net.ccbluex.liquidbounce.ui.client.hud.HUD
import net.ccbluex.liquidbounce.ui.client.hud.HUD.Companion.createDefault
import net.ccbluex.liquidbounce.ui.font.FontLoaders
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.utils.InventoryUtils
import net.ccbluex.liquidbounce.utils.RotationUtils
import org.lwjgl.opengl.Display


object LiquidBounce {
    // Client information
    const val CLIENT_NAME = "Donking"
    const val CLIENT_TIME = "@Donking_dev,Xiao_Dream_dev"
    const val Cldev = "Xiao_Dream_dev"
    const val CLIENT_VERSION = "V1"
    const val CLIENT_VERSIO = "1"
    const val CLIENT_CREATOR = "gy"
    const val CLIENT_CLOUD = "https://cloud.liquidbounce.net/LiquidBounce"
    var isStarting = false
    var height = -14f
    // Managers
    lateinit var tipSoundManager: TipSoundManager
    lateinit var moduleManager: ModuleManager
    lateinit var commandManager: CommandManager
    lateinit var eventManager: EventManager
    lateinit var fileManager: FileManager
    lateinit var scriptManager: ScriptManager
    lateinit var combatManager: CombatManager
    lateinit var user: String
    lateinit var qq: String

    // HUD & ClickGUI
    lateinit var hud: HUD
    lateinit var clickGui: ClickGui
    lateinit var wrapper: Wrapper
    var background: IResourceLocation? = null
    var mainMenuPrep = false
    var USERNAME = ""
    /**
     * Execute if client will be started
     */
    @NativeMethod
    fun startClient() {
        isStarting = true
        Display.setTitle(this.CLIENT_NAME)
        val start = System.currentTimeMillis()
        ClientUtils.getLogger().info("Starting ${this.CLIENT_NAME} ${CLIENT_VERSION}r, by $CLIENT_CREATOR")
        // Create file manager
        fileManager = FileManager()
        // Create event manager
        eventManager = EventManager()
        // Create combat manager
        combatManager = CombatManager()
        // Register listeners
        eventManager.registerListener(RotationUtils())
        eventManager.registerListener(AntiForge())
        eventManager.registerListener(BungeeCordSpoof())
        eventManager.registerListener(InventoryUtils())
        eventManager.registerListener(combatManager)
        eventManager.registerListener(MemoryManager())
        tipSoundManager = TipSoundManager()
        // Create command manager
        commandManager = CommandManager()
        // Load client fonts
        Fonts.loadFonts()
        FontLoaders.initFonts()
        // Setup module manager and register modules
        moduleManager = ModuleManager()
        moduleManager.registerModules()

        // Remapper
        try {
            loadSrg()

            // ScriptManager
            scriptManager = ScriptManager()
            scriptManager.loadScripts()
            scriptManager.enableScripts()
        } catch (throwable: Throwable) {
            ClientUtils.getLogger().error("Failed to load scripts.", throwable)
        }

        // Register commands
        commandManager.registerCommands()

        // Load configs
        fileManager.loadConfigs(
            fileManager.valuesConfig, fileManager.accountsConfig,
            fileManager.friendsConfig, fileManager.xrayConfig
        )



        // ClickGUI
        clickGui = ClickGui()
        fileManager.loadConfig(fileManager.clickGuiConfig)

        // Set HUD
        hud = createDefault()
        fileManager.loadConfig(fileManager.hudConfig)

        // Disable Optifine fast render
        ClientUtils.disableFastRender()

        // Load generators
        GuiAltManager.loadGenerators()
        // Set is starting status
        isStarting = false

        ClientUtils.getLogger().info("Loaded client in " + (System.currentTimeMillis() - start) + " ms.")

        try {
            Display.setTitle(this.CLIENT_NAME + " ")
        } catch (e: Throwable) {
            Display.setTitle(this.CLIENT_NAME + " ")
        }
    }

    /**
     * Execute if client will be stopped
     */
    fun stopClient() {
        // Call client shutdown
        eventManager.callEvent(ClientShutdownEvent())
        // Save all available configs
        fileManager.saveAllConfigs()
        // Stop the client
        System.exit(0)
    }
}