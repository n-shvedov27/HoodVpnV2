apt update -y
bash -c "$(curl -L https://github.com/XTLS/Xray-install/raw/main/install-release.sh)" @ install

mkdir server_config
xray uuid > server_config/vless_uuid
xray x25519 > server_config/vless_keys