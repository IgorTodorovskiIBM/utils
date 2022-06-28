# Build Job : https://128.168.139.253:8443/view/Framework/job/Port-Build/
# This build job will build a utils compatible project and archive the pax.Z artifact
# This job is configured to clone the utils repo at the outset into the current working directory.
# This job must run on the z/OS zot system
# Inputs: 
#   - PORT_GITHUB_URL : e.g: https://github.com/ZOSOpenTools/makeport.git
#   - BRANCH : (default: main)
# Output:
#   - pax.Z artifact is published as a Jenkins artifact
#   - package is copied to /jenkins/build

set -e # Fail on error
set -x # Display verbose output

# Jenkins cannot interpret colours
export NO_COLOR=1

# source bootstrap environment variables on zot
. /jenkins/.env

# Add cloned utils dir to PATH
export PATH="$PWD/bin:$PATH"

# Get port name based on git repo
PORT_NAME=$(basename "${PORT_GITHUB_URL}")
PORT_NAME=${PORT_NAME%%.*}
PORT_NAME=${PORT_NAME%%port}

# Set install dir to workspace/install
export PORT_INSTALL_DIR="${PWD}/install"
mkdir -p ${PORT_INSTALL_DIR}

git clone -b "${BRANCH}" "${PORT_GITHUB_URL}" ${PORT_NAME} && cd ${PORT_NAME}

. ./setenv.sh
build.sh -v

# Copy package to builds dir
mkdir -p /jenkins/builds/
cp -r "${PORT_INSTALL_DIR}" "/jenkins/builds/${PORT_NAME}.${BUILD_TIMESTAMP}"
rm -f "/jenkins/builds/${PORT_NAME}"
ln -s "/jenkins/builds/${PORT_NAME}.${BUILD_TIMESTAMP}" "/jenkins/builds/${PORT_NAME}"
