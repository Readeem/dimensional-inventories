package net.thomilist.dimensionalinventories;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.thomilist.dimensionalinventories.compatibility.Compat;
import net.thomilist.dimensionalinventories.lostandfound.LostAndFound;
import net.thomilist.dimensionalinventories.lostandfound.LostAndFoundContext;
import net.thomilist.dimensionalinventories.module.ModuleGroup;
import net.thomilist.dimensionalinventories.module.ModuleRegistry;
import net.thomilist.dimensionalinventories.module.base.config.ConfigModule;
import net.thomilist.dimensionalinventories.module.base.player.PlayerModule;
import net.thomilist.dimensionalinventories.module.builtin.pool.DimensionPoolTransitionHandler;
import net.thomilist.dimensionalinventories.module.version.StorageVersion;
import net.thomilist.dimensionalinventories.module.version.StorageVersionMigration;
import net.thomilist.dimensionalinventories.util.ModProperties;
import net.thomilist.dimensionalinventories.util.SavePaths;
import net.thomilist.dimensionalinventories.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod( "dimensionalinventories" )
public class DimensionalInventories
{
    public static final ModProperties PROPERTIES = new ModProperties( "dimensionalinventories" );
    public static DimensionalInventories INSTANCE;
    public static final Logger LOGGER = LoggerFactory.getLogger( DimensionalInventories.PROPERTIES.namePascal() );
    public final StorageVersion storageVersion;
    public final StorageVersionMigration storageVersionMigration;
    public final ModuleRegistry<ConfigModule> configModules = new ModuleRegistry<>( ConfigModule.class );
    public final ModuleRegistry<PlayerModule> playerModules = new ModuleRegistry<>( PlayerModule.class );
    public final DimensionPoolTransitionHandler transitionHandler;

    private DimensionalInventories( final StorageVersion storageVersion )
    {
        DimensionalInventories.INSTANCE = this;
        this.storageVersion = storageVersion;

        this.transitionHandler = new DimensionPoolTransitionHandler(
            this.storageVersion,
            this.configModules,
            this.playerModules
        );

        this.storageVersionMigration = new StorageVersionMigration(
            this.storageVersion,
            this.configModules,
            this.transitionHandler
        );
    }

    public DimensionalInventories()
    {
        this( StorageVersion.V2 );

        try ( final LostAndFoundContext LAF = LostAndFound.init(
            "init",
            "base",
            DimensionalInventories.PROPERTIES.id()
        ) )
        {
            this.registerStartupHandlers();
            this.registerPlayerTravelHandler();
            this.registerPlayerRespawnHandler();
            this.registerEntityTravelHandler();
            new net.thomilist.dimensionalinventories.extension.builtin.DimensionalInventoriesExtensionMain().initialize();
        }
    }

    public void registerModules( final ModuleGroup moduleGroup )
    {
        DimensionalInventories.LOGGER.info(
            "Registering modules from module group {} ...",
            StringHelper.joinAndWrapScopes( moduleGroup.groupId() )
        );

        this.configModules.register( moduleGroup );
        this.playerModules.register( moduleGroup );
    }

    private void registerStartupHandlers()
    {
        NeoForge.EVENT_BUS.register( this );
    }

    @SubscribeEvent
    private void onServerStarted( final ServerStartedEvent event )
    {
        try ( final LostAndFoundContext LAF = LostAndFound.init( "server started" ) )
        {
            final var server = event.getServer();

            Compat.onServerStarted( server );
            SavePaths.onServerStarted( server );
            this.storageVersionMigration.tryMigrate( server );

            for ( final ConfigModule config : this.configModules.get( StorageVersion.latest() ) )
            {
                config.loadWithContext();
            }

            DimensionalInventories.LOGGER.info(
                "{} {} initialised",
                DimensionalInventories.PROPERTIES.namePretty(),
                DimensionalInventories.PROPERTIES.version()
            );
        }
    }

    private void registerPlayerTravelHandler()
    {
    }

    @SubscribeEvent
    private void onPlayerChangedDimension( final PlayerEvent.PlayerChangedDimensionEvent event )
    {
        try ( final LostAndFoundContext LAF = LostAndFound.init( "player changed dimension" ) )
        {
            final String originDimensionName = event.getFrom().location().toString();
            final String destinationDimensionName = event.getTo().location().toString();

            this.transitionHandler.handlePlayerDimensionChange(
                (ServerPlayer) event.getEntity(),
                originDimensionName,
                destinationDimensionName
            );
        }
    }

    private void registerPlayerRespawnHandler()
    {
    }

    @SubscribeEvent
    private void onPlayerRespawn( final PlayerEvent.Clone event )
    {
        try ( final LostAndFoundContext LAF = LostAndFound.init( "player respawned" ) )
        {
            final String originDimensionName = event.getOriginal().level().dimension().location().toString();
            final String destinationDimensionName = event.getEntity().level().dimension().location().toString();

            this.transitionHandler.handlePlayerDimensionChange(
                (ServerPlayer) event.getEntity(),
                originDimensionName,
                destinationDimensionName
            );
        }
    }

    private void registerEntityTravelHandler()
    {
    }

    @SubscribeEvent
    private void onEntityTravelToDimension( final EntityTravelToDimensionEvent event )
    {
        if ( event.getEntity() instanceof ServerPlayer )
        {
            return;
        }

        try ( final LostAndFoundContext LAF = LostAndFound.init( "entity changed dimension" ) )
        {
            final String originDimensionName = event.getEntity().level().dimension().location().toString();
            final String destinationDimensionName = event.getDimension().location().toString();

            this.transitionHandler.handleEntityDimensionChange(
                event.getEntity(),
                originDimensionName,
                destinationDimensionName
            );
        }
    }
}
