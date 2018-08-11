#!/usr/bin/env bash

export APP_MODULE_PATH="app"
export JSON_PATH="$APP_MODULE_PATH/google-services.json"

if [ ! -e ${JSON_PATH} ]; then
    echo "Writing mock google-services.json file to $JSON_PATH..."

    echo '{
      "project_info": {
        "project_number": "012345678912",
        "firebase_url": "https://wonderandwander-mock.firebaseio.com",
        "project_id": "wonderandwander-mock",
        "storage_bucket": "wonderandwander-mock.appspot.com"
      },
      "client": [
        {
          "client_info": {
            "mobilesdk_app_id": "'${MOBILE_SDK_APP_ID_FREE}'",
            "android_client_info": {
              "package_name": "com.projects.melih.wonderandwander.free"
            }
          },
          "oauth_client": [
            {
              "client_id": "'${CLIENT_ID_FREE}'.apps.googleusercontent.com",
              "client_type": 1,
              "android_info": {
                "package_name": "com.projects.melih.wonderandwander.free",
                "certificate_hash": "'${CERTIFICATE_HASH_FREE}'"
              }
            },
            {
              "client_id": "'${SECOND_CLIENT_ID_FREE}'.apps.googleusercontent.com",
              "client_type": 3
            }
          ],
          "api_key": [
            {
              "current_key": "'${FIRST_CURRENT_KEY}'"
            },
            {
              "current_key": "'${SECOND_CURRENT_KEY}'"
            }
          ],
          "services": {
            "analytics_service": {
              "status": 1
            },
            "appinvite_service": {
              "status": 1,
              "other_platform_oauth_client": [
                {
                  "client_id": "'${SECOND_CLIENT_ID_FREE}'.apps.googleusercontent.com",
                  "client_type": 3
                }
              ]
            },
            "ads_service": {
              "status": 2
            }
          }
          "admob_app_id": "'${ADMOB_APP_ID}'"
        },
        {
          "client_info": {
            "mobilesdk_app_id": "'${MOBILE_SDK_APP_ID_PAID}'",
            "android_client_info": {
              "package_name": "com.projects.melih.wonderandwander.paid"
            }
          },
          "oauth_client": [
            {
              "client_id": "'${CLIENT_ID_PAID}'.apps.googleusercontent.com",
              "client_type": 1,
              "android_info": {
                "package_name": "com.projects.melih.wonderandwander.paid",
                "certificate_hash": "'${CERTIFICATE_HASH_PAID}'"
              }
            },
            {
              "client_id": "'${SECOND_CLIENT_ID_PAID}'.apps.googleusercontent.com",
              "client_type": 3
            }
          ],
          "api_key": [
            {
              "current_key": "'${FIRST_CURRENT_KEY}'"
            },
            {
              "current_key": "'${SECOND_CURRENT_KEY}'"
            }
          ],
          "services": {
            "analytics_service": {
              "status": 1
            },
            "appinvite_service": {
              "status": 2,
              "other_platform_oauth_client": [
                {
                  "client_id": "'${SECOND_CLIENT_ID_PAID}'.apps.googleusercontent.com",
                  "client_type": 3
                }
              ]
            },
            "ads_service": {
              "status": 2
            }
          }
        }
      ],
      "configuration_version": "1"
    }' > ${JSON_PATH}

    echo "Done."
else
    echo "The $JSON_PATH file already exists, skipping..."
fi
