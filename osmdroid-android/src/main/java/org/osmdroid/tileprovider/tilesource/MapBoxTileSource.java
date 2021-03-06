/**
 * @author Brad Leege <bleege@gmail.com>
 * Created on 10/15/13 at 7:57 PM
 */

package org.osmdroid.tileprovider.tilesource;

import android.content.Context;
import android.util.Log;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.util.ManifestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapBoxTileSource extends OnlineTileSourceBase
{
    private static Logger logger = LoggerFactory.getLogger(MapBoxTileSource.class);

    /** the meta data key in the manifest */
    private static final String MAPBOX_MAPID = "MAPBOX_MAPID";

    private static String mapBoxMapId = "";

    private static String mapBoxBaseUrl = "http://api.tiles.mapbox.com/v3/";

    /**
     * TileSource with configuration defaults set.
     * <br> <b>Warning, the static method {@link #retrieveMapBoxMapId(android.content.Context)} should have been invoked once before constructor invocation</b>
     */
    public MapBoxTileSource()
    {
        super("mbtiles", ResourceProxy.string.base, 1, 20, 256, ".png", mapBoxBaseUrl);
    }

    /**
     * TileSource allowing majority of options (sans url) to be user selected.
     * <br> <b>Warning, the static method {@link #retrieveMapBoxMapId(android.content.Context)} should have been invoked once before constructor invocation</b>
     * @param name Name
     * @param resourceId Resource Id
     * @param zoomMinLevel Minimum Zoom Level
     * @param zoomMaxLevel Maximum Zoom Level
     * @param tileSizePixels Size of Tile Pixels
     * @param imageFilenameEnding Image File Extension
     */
    public MapBoxTileSource(String name, ResourceProxy.string resourceId, int zoomMinLevel, int zoomMaxLevel, int tileSizePixels, String imageFilenameEnding, String mapBoxMapId)
    {
        super(name, resourceId, zoomMinLevel, zoomMaxLevel, tileSizePixels, imageFilenameEnding, mapBoxBaseUrl);
    }

    /**
     * TileSource allowing all options to be user selected.
     * <br> <b>Warning, the static method {@link #retrieveMapBoxMapId(android.content.Context)} should have been invoked once before constructor invocation</b>
     * @param name Name
     * @param resourceId Resource Id
     * @param zoomMinLevel Minimum Zoom Level
     * @param zoomMaxLevel Maximum Zoom Level
     * @param tileSizePixels Size of Tile Pixels
     * @param imageFilenameEnding Image File Extension
     * @param mapBoxVersionBaseUrl MapBox Version Base Url @see https://www.mapbox.com/developers/api/#Versions
     */
    public MapBoxTileSource(String name, ResourceProxy.string resourceId, int zoomMinLevel, int zoomMaxLevel, int tileSizePixels, String imageFilenameEnding, String mapBoxMapId, String mapBoxVersionBaseUrl)
    {
        super(name, resourceId, zoomMinLevel, zoomMaxLevel, tileSizePixels, imageFilenameEnding, mapBoxVersionBaseUrl);
    }

    /**
     * Read the API key from the manifest.<br>
     * This method should be invoked before class instantiation.<br>
     */
    public static void retrieveMapBoxMapId(final Context aContext)
    {
        // Retrieve the MapId from the Manifest
        mapBoxMapId = ManifestUtil.retrieveKey(aContext, MAPBOX_MAPID);
    }

    public static String getMapBoxMapId()
    {
        return mapBoxMapId;
    }

    @Override
    public String getTileURLString(MapTile mapTile)
    {
        StringBuffer url = new StringBuffer(getBaseUrl());
        url.append(getMapBoxMapId());
        url.append("/");
        url.append(mapTile.getZoomLevel());
        url.append("/");
        url.append(mapTile.getX());
        url.append("/");
        url.append(mapTile.getY());
        url.append(".png");

        String res = url.toString();
        Log.i(getClass().getCanonicalName(), "URL = '" + res  + "'");

        return res;
    }
}