#!/usr/bin/env bash
export KEYSTORE_PROPERTIES=$HOME"/wonderandwander/keystore.properties"
export FABRIC_PROPERTIES=$HOME"/wonderandwander/app/fabric.properties"
export STORE_FILE_LOCATION=$HOME"/wonderandwander/${STORE_FILE}"

function copyEnvVarsToProperties {

    echo "Keystore Properties should exist at $KEYSTORE_PROPERTIES"

    if [ ! -f "$KEYSTORE_PROPERTIES" ]
    then
        echo "${KEYSTORE_PROPERTIES} does not exist...Creating file"

        touch ${KEYSTORE_PROPERTIES}

        echo "keyAlias=${KEY_ALIAS}" >> ${KEYSTORE_PROPERTIES}
        echo "keyPassword=${KEY_PASSWORD}" >> ${KEYSTORE_PROPERTIES}
        echo "storeFile=${STORE_FILE_LOCATION}" >> ${KEYSTORE_PROPERTIES}
        echo "storePassword ${STORE_PASSWORD}" >> ${KEYSTORE_PROPERTIES}
    fi

    echo "Keystore Properties should exist at $KEYSTORE_PROPERTIES"

    if [ ! -f "$FABRIC_PROPERTIES" ]
    then
        echo "${FABRIC_PROPERTIES} does not exist...Creating file"

        touch ${FABRIC_PROPERTIES}

        echo "apiSecret=${FABRIC_KEY}" >> ${FABRIC_PROPERTIES}
    fi
}


# download key store file from remote location
# keystore URI will be the location uri for the *.jks file for signing application
function downloadKeyStoreFile {
    # use curl to download a keystore from $KEYSTORE_URI, if set,
    # to the path/filename set in $KEYSTORE.
    echo "Looking for $STORE_FILE_LOCATION ..."

    if [ ! -f ${STORE_FILE_LOCATION} ] ; then
        echo "Keystore file is missing, performing download for ${STORE_FILE} at ${KEY_STORE_URI}"
        # we're using curl instead of wget because it will not
        # expose the sensitive uri in the build logs:
        curl -L -o ${STORE_FILE} ${KEY_STORE_URI}
    else
            echo "Keystore uri not set.  .APK artifact will not be signed."
    fi
}

# execute functions
copyEnvVarsToProperties
downloadKeyStoreFile