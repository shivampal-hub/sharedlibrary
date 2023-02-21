def readPropertyFile() {
  readProp = read Properties file: 'configFilePath'
  return readProp
}

def clone() {
  git branch: 'master', url: 'https://github.com/shivampal-hub/ansible.git'
}

def approval() {
  def p = readPropertyFile()
  if ("${p.KEEP_APPROVAL_STAGE}" == "true") {
    input 'Do you want to execute playbook?'
  }
}

def playbookExecution() {
  ansiblePlaybook credentialsId: 'assignment-ssh', disableHostKeyChecking: true, installation: 'ansible1', inventory: 'inv', playbook: 'role_jenkins.yml'
}

def slackSend(String buildResult) {
  def p = readPropertyFile()
  if ( buildResult == "Success" ) {
    slackSend channel: "${p.SLACK_CHANNEL_NAME}",
      message: "${p.ACTION_MESSAGE}",
      tokenCredentialId: 'slack-token'
  }
  else if ( buildResult == "FAILURE") {
    slackSend channel: "${p.SLACK_CHANNEL_NAME}",
    message: "${p.ACTION_MESSAGE}",
      tokenCredentialId: 'slack-token'
  }
  else if ( buildResult == "UNSTABLE") {
    slackSend channel: "${p.SLACK_CHANNEL_NAME}",
    message: "${p.ACTION_MESSAGE}",
      tokenCredentialId: 'slack-token'
  }
  else {
    slackSend channel: "${p.SLACK_CHANNEL_NAME}",
      message: "${p.ACTION_MESSAGE}",
      tokenCredentialId: 'slack-token'
  }
}
      
  
