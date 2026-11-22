package coach.zander.chronicler.cli.gen.xml;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.QuietReporter;

/**
 * ApprovalTests settings for this package: approved files live under
 * src/test/resources/.../approved, and a failing approval only prints the command to accept it
 * instead of opening a diff tool.
 */
public class PackageSettings {
  public static String ApprovalBaseDirectory = "../resources";
  public static String UseApprovalSubdirectory = "approved";
  public static ApprovalFailureReporter UseReporter = QuietReporter.INSTANCE;
}
