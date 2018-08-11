#!/usr/bin/env bash

export APP_MODULE_PATH="app"
export FREE_XML_PATH="$APP_MODULE_PATH/src/free/res/values/keys.xml"
export PAID_XML_PATH="$APP_MODULE_PATH/src/paid/res/values/keys.xml"

if [ ! -e ${FREE_XML_PATH} ]; then
    echo "Writing mock keys.xml file to $FREE_XML_PATH..."

    echo '<?xml version="1.0" encoding="utf-8"?>
      <resources>
        <string name="banner_ad_id">'${FREE_BANNER_ADD_ID}'</string>
        <string name="admob_app_id">'${ADMOB_APP_ID}'</string>
        <string name="map_id">'${FREE_MAP_ID}'</string>
      </resources>
    ' > ${FREE_XML_PATH}

    echo "Done."
else
    echo "The $FREE_XML_PATH file already exists, skipping..."
fi

if [ ! -e ${PAID_XML_PATH} ]; then
    echo "Writing mock keys.xml file to $FREE_XML_PATH..."

    echo '<?xml version="1.0" encoding="utf-8"?>
      <resources>
        <string name="banner_ad_id"></string>
        <string name="admob_app_id"></string>
        <string name="map_id">'${PAID_MAP_ID}'</string>
      </resources>
    ' > ${PAID_XML_PATH}

    echo "Done."
else
    echo "The $PAID_XML_PATH file already exists, skipping..."
fi
