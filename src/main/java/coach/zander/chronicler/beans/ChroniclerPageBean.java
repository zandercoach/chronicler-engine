package coach.zander.cfk.beans;

public abstract class CfkPageBean {
  private NavigationBean navigation;
  private String pageTitle;

  public NavigationBean getNavigation() {
    return navigation;
  }

  public String getPageTitle() {
    return pageTitle;
  }

  public void setNavigation(NavigationBean navigation) {
    this.navigation = navigation;
  }

  public void setPageTitle(String pageTitle) {
    this.pageTitle = pageTitle;
  }
}
