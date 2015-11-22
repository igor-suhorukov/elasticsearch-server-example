@Grab(group='com.github.igor-suhorukov', module='attach-vm', version='1.0')
import com.github.igorsuhorukov.jvmattachapi.VirtualMachineUtils;
import com.sun.tools.attach.VirtualMachine;

import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader;

def aspectJScriptingFile = MavenClassLoader.forMavenCoordinates("com.github.igor-suhorukov:aspectj-scripting:jar:agent:1.3").getURLs().getAt(0).getFile()

println aspectJScriptingFile

def processId = this.args.getAt(0) //CliBuilder
def configPath = this.args.getAt(1)

println processId
println configPath

VirtualMachine virtualMachine = VirtualMachineUtils.connectToVirtualMachine(processId)
try {
    virtualMachine.loadAgent(aspectJScriptingFile, configPath)
} finally {
    virtualMachine.detach()
}
