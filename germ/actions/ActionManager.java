package germ.actions;



/**
 * Klasa koja sadrzi instance svih akcija kao polja. Koristi se za pristupanje akcijama.
 */
public class ActionManager {

	private FileSaveAction fileSaveAction;
	
	private ChangeWorkspaceAction changeWorkspaceAction;

	private QuitAction quitAction;

	private ZoomAction zoomAction;

	private ZoomBestFitAction zoomBestFitAction;

	private ZoomBestFitSelectionAction zoomBestFitSelectionAction;

	private ZoomLasoAction zoomLasoAction;

	private UndoAction undoAction;

	private RedoAction redoAction;

	private CutAction cutAction;

	private CopyAction copyAction;

	private PasteAction pasteAction;

	private RequirementAction requirementAction;

	private ArgumentAction argumentAction;

	private AssumptionAction assumptionAction;

	private DecisionAction decisionAction;

	private StakeholderAction stakeholderAction;

	private TopicAction topicAction;

	private PositionAction positionAction;

	private LinkAction linkAction;

	private RequirementPropertiesAction requirementPropertiesAction;

    private ShowColorPropertiesAction showColorPropertiesAction;
    
	private ArgumentPropertiesAction argumentPropertiesAction;

	private AssumptionPropertiesAction assumptionPropertiesAction;

	private DecisionPropertiesAction decisionPropertiesAction;

	private StakeholderPropertiesAction stakeholderPropertiesAction;

	private TopicPropertiesAction topicPropertiesAction;

	private PositionPropertiesAction positionPropertiesAction;
	
	private FlipNodeAction flipAction;

	private DeleteAction deleteAction;
	
	private ShowPropertyAction showPropertyAction;
	
	private InverseSelectionAction inverseSelectionAction;
	
	private SelectAllAction selectAllAction;
	
	private SelectAllArgumentsAction selectAllArgumentsAction;
	private SelectAllAssumptionsAction selectAllAssumptionsAction;
	private SelectAllDecisionsAction selectAllDecisionsAction;
	private SelectAllPositionsAction selectAllPositionsAction;
	private SelectAllRequirementsAction selectAllRequirementsAction;
	private SelectAllStakeholdersAction selectAllStakeholdersAction;
	private SelectAllTopicsAction selectAllTopicsAction;
	private SelectLinkedNodesAction selectLinkedAction;
	
	private SettingsAction settingsAction;
	
	private SwitchShowGridAction switchShowGridAction;
	
	private TabCloseAction tabCloseAction;
	private TabCloseOthersAction tabCloseOthersAction;
	
	private CreateNewProjectAction createNewProjectAction;
	private CreateNewDiagramAction createNewDiagramAction;
	
	private FindReplaceAction findReplaceAction;
	
	private RenameDiagramAction renameDiagramAction;
	private RenameProjectAction renameProjectAction;

	private OpenSubDiagramAction openSubDiagramAction;

	private CenterNodeAction centerNodeAction;
	private AboutAction aboutAction;
	
	private InsertLinkAuthorAction linkAuthorAction;
	private InsertLinkRefineAction linkRefineAction;
	private InsertLinkInterestAction linkInterestAction;
	private InsertLinkDependencyAction linkDependencyAction;
	private InsertLinkSupportAction linkSupportAction;
	
	private ExportAction exportAction;
	private ImportAction importAction;
	private DeleteDiagramAction deleteDiagramAction;
	private DeleteProjectAction deleteProjectAction;
	
	private AlignTopAction alignTopAction;
	private AlignBottomAction alignBottomAction;
	private AlignLeftAction alignLeftAction;
	private AlignRightAction alignRightAction;
	
	private DistributeHorizontalAction distributeHorizontalAction;
	private DistributeVerticalAction distributeVerticalAction;
	
	private HelpAction helpAction;
	
	public ActionManager() {
		initialiseActions();
	}

	/**
	 * Metoda za inicijalizaciju akcija, polja klase.
	 */
	private void initialiseActions() {
		fileSaveAction = new FileSaveAction();
		changeWorkspaceAction = new ChangeWorkspaceAction();
		quitAction = new QuitAction();
		undoAction = new UndoAction();
		redoAction = new RedoAction();
		cutAction = new CutAction();
		copyAction = new CopyAction();
		pasteAction = new PasteAction();
		pasteAction.setEnabled(false);
		zoomAction = new ZoomAction();
		zoomBestFitAction = new ZoomBestFitAction();
		zoomBestFitSelectionAction = new ZoomBestFitSelectionAction();
		zoomLasoAction = new ZoomLasoAction();
		deleteAction = new DeleteAction();

		requirementAction = new RequirementAction();
		argumentAction = new ArgumentAction();
		assumptionAction = new AssumptionAction();
		decisionAction = new DecisionAction();
		stakeholderAction = new StakeholderAction();
		topicAction = new TopicAction();
		positionAction = new PositionAction();
		
		flipAction = new FlipNodeAction();

		linkAction = new LinkAction();

		stakeholderPropertiesAction = new StakeholderPropertiesAction();
		requirementPropertiesAction = new RequirementPropertiesAction();
		assumptionPropertiesAction = new AssumptionPropertiesAction();
		argumentPropertiesAction = new ArgumentPropertiesAction();
		topicPropertiesAction = new TopicPropertiesAction();
		decisionPropertiesAction = new DecisionPropertiesAction();
		positionPropertiesAction = new PositionPropertiesAction();

		showPropertyAction = new ShowPropertyAction();
		
		inverseSelectionAction = new InverseSelectionAction();
		selectAllAction = new SelectAllAction();
		
		selectAllArgumentsAction = new SelectAllArgumentsAction();
		selectAllAssumptionsAction = new SelectAllAssumptionsAction();
		selectAllDecisionsAction = new SelectAllDecisionsAction();
		selectAllPositionsAction = new SelectAllPositionsAction();
		selectAllRequirementsAction = new SelectAllRequirementsAction();
		selectAllStakeholdersAction = new SelectAllStakeholdersAction();
		selectAllTopicsAction = new SelectAllTopicsAction();
		
		selectLinkedAction = new SelectLinkedNodesAction();

		showColorPropertiesAction = new ShowColorPropertiesAction();
		settingsAction = new SettingsAction();
		switchShowGridAction = new SwitchShowGridAction();
		
		createNewProjectAction = new CreateNewProjectAction();
		createNewDiagramAction = new CreateNewDiagramAction();
		
		findReplaceAction = new FindReplaceAction();
		tabCloseAction = new TabCloseAction();
		tabCloseOthersAction = new TabCloseOthersAction();
		
		renameDiagramAction = new RenameDiagramAction();
		renameProjectAction = new RenameProjectAction();
		
		openSubDiagramAction = new OpenSubDiagramAction();
		
		centerNodeAction = new CenterNodeAction();
		aboutAction = new AboutAction();
		
		linkAuthorAction = new InsertLinkAuthorAction();
		linkDependencyAction = new InsertLinkDependencyAction();
		linkInterestAction = new InsertLinkInterestAction();
		linkRefineAction = new InsertLinkRefineAction();
		linkSupportAction = new InsertLinkSupportAction();
		
		exportAction = new ExportAction();
		importAction = new ImportAction();
		
		deleteDiagramAction = new DeleteDiagramAction();
		deleteProjectAction = new DeleteProjectAction();
		
		alignBottomAction = new AlignBottomAction();
		alignTopAction = new AlignTopAction();
		alignLeftAction = new AlignLeftAction();
		alignRightAction = new AlignRightAction(); 
		
		distributeHorizontalAction = new DistributeHorizontalAction();
		distributeVerticalAction = new DistributeVerticalAction();
		
		helpAction = new HelpAction();
		
	}

	public DeleteAction getDeleteAction() {
		return deleteAction;
	}

	public CutAction getCutAction() {
		return cutAction;
	}

	public CopyAction getCopyAction() {
		return copyAction;
	}

	public PasteAction getPasteAction() {
		return pasteAction;
	}

	public UndoAction getUndoAction() {
		return undoAction;
	}

	public RedoAction getRedoAction() {
		return redoAction;
	}

	public AssumptionAction getAssumptionAction() {
		return assumptionAction;
	}

	public DecisionAction getDecisionAction() {
		return decisionAction;
	}

	public StakeholderAction getStakeholderAction() {
		return stakeholderAction;
	}

	public PositionAction getPositionAction() {
		return positionAction;
	}

	public TopicAction getTopicAction() {
		return topicAction;
	}

	public FileSaveAction getFileSaveAction() {
		return fileSaveAction;
	}

	public QuitAction getQuitAction() {
		return quitAction;
	}

	public RequirementAction getRequirementAction() {
		return requirementAction;
	}

	public LinkAction getLinkAction() {
		return linkAction;
	}

	public ZoomAction getZoomAction() {
		return zoomAction;
	}

	public ZoomBestFitAction getZoomBestFitAction() {
		return zoomBestFitAction;
	}

	public ZoomBestFitSelectionAction getZoomBestFitSelectionAction() {
		return zoomBestFitSelectionAction;
	}

	public ZoomLasoAction getZoomLasoAction() {
		return zoomLasoAction;
	}

	public ShowPropertyAction getShowPropertyAction() {
		return showPropertyAction;
	}
	
	public ShowColorPropertiesAction getShowColorPropertiesAction(){
		return showColorPropertiesAction;
	}

	public ArgumentAction getArgumentAction() {
		return argumentAction;
	}

	public TopicPropertiesAction getTopicPropertiesAction() {
		return topicPropertiesAction;
	}

	public ArgumentPropertiesAction getArgumentPropertiesAction() {
		return argumentPropertiesAction;
	}

	public StakeholderPropertiesAction getStakeholderPropertiesAction() {
		return stakeholderPropertiesAction;
	}

	public RequirementPropertiesAction getRequirementPropertiesAction() {
		return requirementPropertiesAction;
	}

	public DecisionPropertiesAction getDecisionPropertiesAction() {
		return decisionPropertiesAction;
	}

	public PositionPropertiesAction getPositionPropertiesAction() {
		return positionPropertiesAction;
	}

	public AssumptionPropertiesAction getAssumptionPropertiesAction() {
		return assumptionPropertiesAction;
	}

	public InverseSelectionAction getInverseSelectionAction() {
		return inverseSelectionAction;
	}

	public SelectAllAction getSelectAllAction() {
		return selectAllAction;
	}
	
	public SelectAllArgumentsAction getSelectAllArgumentsAction() {
        return selectAllArgumentsAction;
    }

    public SelectAllAssumptionsAction getSelectAllAssumptionsAction() {
        return selectAllAssumptionsAction;
    }

    public SelectAllDecisionsAction getSelectAllDecisionsAction() {
        return selectAllDecisionsAction;
    }

    public SelectAllPositionsAction getSelectAllPositionsAction() {
        return selectAllPositionsAction;
    }

    public SelectAllRequirementsAction getSelectAllRequirementsAction() {
        return selectAllRequirementsAction;
    }

    public SelectAllStakeholdersAction getSelectAllStakeholdersAction() {
        return selectAllStakeholdersAction;
    }

    public SelectAllTopicsAction getSelectAllTopicsAction() {
        return selectAllTopicsAction;
    }

    public SelectLinkedNodesAction getSelectLinkedAction() {
        return selectLinkedAction;
    }

    public SettingsAction getSettingsAction(){
		return settingsAction;
	}
	
	public SwitchShowGridAction getSwitchShowGridAction() {
        return switchShowGridAction;
    }

	public ChangeWorkspaceAction getChangeWorkspaceAction() {
		return changeWorkspaceAction;
	}
	
	public CreateNewProjectAction getCreateNewProjectAction(){
		return createNewProjectAction;
	}
	
	public CreateNewDiagramAction getCreateNewDiagramAction(){
		return createNewDiagramAction;
	}

    public FlipNodeAction getFlipAction() {
        return flipAction;
    }
    
    public FindReplaceAction getFindReplaceAction(){
    	return findReplaceAction;
    }

	public TabCloseAction getTabCloseAction() {
		return tabCloseAction;
	}

	public TabCloseOthersAction getTabCloseOthersAction() {
		return tabCloseOthersAction;
	}
	
	public RenameDiagramAction getRenameDiagramAction(){
		return renameDiagramAction;
	}
	
	public RenameProjectAction getRenameProjectAction(){
		return renameProjectAction;
	}

	public OpenSubDiagramAction getOpenSubDiagramAction() {
		return openSubDiagramAction;
	}
	
	public CenterNodeAction getCenterNodeAction(){
		return centerNodeAction;
	}
	
	public AboutAction getAboutAction(){
		return aboutAction;
	}

	public InsertLinkAuthorAction getLinkAuthorAction() {
		return linkAuthorAction;
	}

	public InsertLinkRefineAction getLinkRefineAction() {
		return linkRefineAction;
	}

	public InsertLinkInterestAction getLinkInterestAction() {
		return linkInterestAction;
	}

	public InsertLinkDependencyAction getLinkDependencyAction() {
		return linkDependencyAction;
	}

	public InsertLinkSupportAction getLinkSupportAction() {
		return linkSupportAction;
	}
	
	public ExportAction getExportACtion(){
		return exportAction;
	}
	
	public ImportAction getImportAction(){
		return importAction;
	}
	
	public DeleteDiagramAction getDeleteDiagramAction(){
		return deleteDiagramAction;
	}
	
	public DeleteProjectAction getDeleteProjectAction(){
		return deleteProjectAction;
	}

    public AlignTopAction getAlignTopAction() {
        return alignTopAction;
    }

    public AlignBottomAction getAlignBottomAction() {
        return alignBottomAction;
    }

    public AlignLeftAction getAlignLeftAction() {
        return alignLeftAction;
    }

    public AlignRightAction getAlignRightAction() {
        return alignRightAction;
    }

    public DistributeHorizontalAction getDistributeHorizontalAction() {
        return distributeHorizontalAction;
    }

    public DistributeVerticalAction getDistributeVerticalAction() {
        return distributeVerticalAction;
    }
    
    public HelpAction getHelpAction() {
    	return helpAction;
    }
}
