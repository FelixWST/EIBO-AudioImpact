package presentation.mainView.titleView;

import business.managing.Project;

public class TitleViewController {

    private TitleView root;
    private Project project;

    public TitleViewController(Project project){
        this.root = new TitleView();
        this.project = project;
        root.titleLabel.setText(project.getProjectTitle());
    }

    public TitleView getRoot(){
        return this.root;
    }
}
