package libraries;

import aura.Aura;

public class AuraSetup 
{
	Aura oAura = new Aura();

	public AuraSetup(Parameters oParameters) 
	{
		if (oParameters.GetParameters("Aura_Update").equalsIgnoreCase("YES")) 
		{
			try 
			{
				if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
					oParameters.SetParameters("ALM_TESTSET_NAME", "CCM_Automation");
				else if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT"))
					oParameters.SetParameters("ALM_TESTSET_NAME", "Automation_DC");
				else
					oParameters.SetParameters("ALM_TESTSET_NAME", "Automation_DT");

				String description = oAura.ALMDataCapture(oParameters.GetParameters("ALM_USER_NAME"),
										oParameters.GetParameters("ALM_PASSWORD"), oParameters.GetParameters("ALM_HOST"),
											oParameters.GetParameters("ALM_DOMAIN"), oParameters.GetParameters("ALM_PROJECT"),
												oParameters.GetParameters("ALM_PARENT_FOLDER"), oParameters.GetParameters("ALM_TESTSET_NAME"),
													oParameters.GetParameters("TESTNAME"), oParameters.GetParameters("JIRA_HOST"));

				if (description.equalsIgnoreCase(null) || description.equalsIgnoreCase("")|| description.equalsIgnoreCase("|"))
					System.out.println("No JIRA associated with previous run of this VR");
				else 
				{
					String[] desc1 = description.split("\\|");
					oParameters.SetParameters("OLD_JIRA_DISCRIPTION", desc1[1]);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void ALM_JIRA_Update(Parameters oParameters) 
	{
		if (oParameters.GetParameters("Aura_Update").equalsIgnoreCase("YES")) 
		{
			try 
			{
				String Run = oAura.eAura(oParameters.GetParameters("TestStatus"),oParameters.GetParameters("HTML_REPORT_NAME"), oParameters.GetParameters("REPORT_FORMAT"));
				System.out.println(Run);
				String JD = oAura.JiraDetails(oParameters.GetParameters("JIRA_HOST"),
								oParameters.GetParameters("JIRA_USER_NAME"), 
									oParameters.GetParameters("JIRA_PASSWORD"),
										oParameters.GetParameters("JIRA_PROJECT"), 
											oParameters.GetParameters("JIRA_SOLUTION_NAME"),
												oParameters.GetParameters("JIRADESCRIPTION"),
													oParameters.GetParameters("ALWAYS_CREATE_NEW_JIRA"));
				System.out.println(JD);
			}
			catch (Exception e) 
			{
				System.out.println("Exception Caught :" + e);
			}
		} 
		else 
		{
			System.out.println("Aura update is disabled");
		}
	}
}
