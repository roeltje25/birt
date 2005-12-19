/***********************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.chart.ui.swt.composites;

import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.model.attribute.ActionType;
import org.eclipse.birt.chart.model.attribute.ActionValue;
import org.eclipse.birt.chart.model.attribute.AttributeFactory;
import org.eclipse.birt.chart.model.attribute.ScriptValue;
import org.eclipse.birt.chart.model.attribute.SeriesValue;
import org.eclipse.birt.chart.model.attribute.TooltipValue;
import org.eclipse.birt.chart.model.attribute.TriggerCondition;
import org.eclipse.birt.chart.model.attribute.URLValue;
import org.eclipse.birt.chart.model.attribute.impl.TooltipValueImpl;
import org.eclipse.birt.chart.model.attribute.impl.URLValueImpl;
import org.eclipse.birt.chart.model.data.Action;
import org.eclipse.birt.chart.model.data.Trigger;
import org.eclipse.birt.chart.model.data.impl.ActionImpl;
import org.eclipse.birt.chart.model.data.impl.TriggerImpl;
import org.eclipse.birt.chart.ui.extension.i18n.Messages;
import org.eclipse.birt.chart.ui.swt.interfaces.IUIServiceProvider;
import org.eclipse.birt.chart.ui.swt.wizard.ChartWizard;
import org.eclipse.birt.chart.ui.swt.wizard.ChartWizardContext;
import org.eclipse.birt.chart.util.LiteralHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author Actuate Corporation
 * 
 */
public class TriggerDataComposite extends Composite
		implements
			SelectionListener
{

	private transient Group grpValue = null;

	private transient Composite cmpURL = null;

	// private transient Text txtBaseURL = null;

	// private transient Text txtTarget = null;

	private transient Group grpParameters = null;

	private transient Text txtBaseParm = null;

	private transient Text txtValueParm = null;

	private transient Text txtSeriesParm = null;

	private transient Composite cmpCallback = null;

	private transient Composite cmpDefault = null;

	private transient Composite cmpScript = null;

	private transient Text txtScript = null;

	private transient Composite cmpTooltip = null;

	private transient IntegerSpinControl iscDelay = null;

	private transient Text txtTooltipText = null;

	private transient Composite cmpVisiblity = null;

	private transient Composite cmpHighlight = null;

	// private transient Text txtSeriesDefinition = null;

	private transient StackLayout slValues = null;

	private transient Combo cmbTriggerType = null;

	private transient Combo cmbActionType = null;

	private transient ChartWizardContext wizardContext;

	private transient Button btnBaseURL;

	private transient String sBaseURL = ""; //$NON-NLS-1$

	private transient boolean bEnableURLParameters;

	private transient boolean bEnableShowTooltipValue;

	/**
	 * 
	 * @param parent
	 * @param style
	 * @param trigger
	 * @param wizardContext
	 */
	public TriggerDataComposite( Composite parent, int style, Trigger trigger,
			ChartWizardContext wizardContext, boolean bEnableURLParameters,
			boolean bEnableShowTooltipValue )
	{
		super( parent, style );
		this.wizardContext = wizardContext;
		this.bEnableURLParameters = bEnableURLParameters;
		this.bEnableShowTooltipValue = bEnableShowTooltipValue;
		init( );
		placeComponents( );
		setTrigger( trigger );
	}

	private void init( )
	{
		this.setSize( getParent( ).getClientArea( ).width,
				getParent( ).getClientArea( ).height );
	}

	private void placeComponents( )
	{
		// Layout for the content composite
		GridLayout glCMPTrigger = new GridLayout( );
		glCMPTrigger.numColumns = 2;
		glCMPTrigger.horizontalSpacing = 16;
		glCMPTrigger.verticalSpacing = 5;
		glCMPTrigger.marginHeight = 0;
		glCMPTrigger.marginWidth = 0;

		// Layout for the Action Details group
		slValues = new StackLayout( );

		// Layout for url value composite
		GridLayout glURL = new GridLayout( );
		glURL.marginWidth = 2;
		glURL.marginHeight = 6;
		glURL.horizontalSpacing = 6;
		glURL.numColumns = 3;

		// Layout for script value composite
		GridLayout glParameter = new GridLayout( );
		glParameter.marginWidth = 2;
		glParameter.marginHeight = 6;
		glParameter.horizontalSpacing = 6;
		glParameter.numColumns = 3;

		// Main content composite
		this.setLayout( glCMPTrigger );

		Label lblTriggerType = new Label( this, SWT.NONE );
		GridData gdLBLTriggerType = new GridData( );
		gdLBLTriggerType.horizontalIndent = 4;
		lblTriggerType.setLayoutData( gdLBLTriggerType );
		lblTriggerType.setText( Messages.getString( "TriggerDataComposite.Lbl.Type" ) ); //$NON-NLS-1$

		cmbTriggerType = new Combo( this, SWT.DROP_DOWN | SWT.READ_ONLY );
		GridData gdCMBTriggerType = new GridData( GridData.FILL_HORIZONTAL );
		cmbTriggerType.setLayoutData( gdCMBTriggerType );
		cmbTriggerType.addSelectionListener( this );

		Label lblActionType = new Label( this, SWT.NONE );
		GridData gdLBLActionType = new GridData( );
		gdLBLActionType.horizontalIndent = 4;
		lblActionType.setLayoutData( gdLBLActionType );
		lblActionType.setText( Messages.getString( "TriggerDataComposite.Lbl.Action" ) ); //$NON-NLS-1$

		cmbActionType = new Combo( this, SWT.DROP_DOWN | SWT.READ_ONLY );
		GridData gdCMBActionType = new GridData( GridData.FILL_HORIZONTAL );
		cmbActionType.setLayoutData( gdCMBActionType );
		cmbActionType.addSelectionListener( this );

		grpValue = new Group( this, SWT.NONE );
		GridData gdGRPValue = new GridData( GridData.FILL_BOTH );
		gdGRPValue.horizontalSpan = 2;
		grpValue.setLayoutData( gdGRPValue );
		grpValue.setText( Messages.getString( "TriggerDataComposite.Lbl.ActionDetails" ) ); //$NON-NLS-1$
		grpValue.setLayout( slValues );

		// Composite for default value
		cmpDefault = new Composite( grpValue, SWT.NONE );

		// Composite for callback value
		cmpCallback = new Composite( grpValue, SWT.NONE );
		cmpCallback.setLayout( new GridLayout( ) );

		addDescriptionLabel( cmpCallback,
				1,
				Messages.getString( "TriggerDataComposite.Label.CallbackDescription" ) ); //$NON-NLS-1$

		// Composite for highlight value
		cmpHighlight = new Composite( grpValue, SWT.NONE );
		cmpHighlight.setLayout( new GridLayout( ) );

		addDescriptionLabel( cmpHighlight,
				1,
				Messages.getString( "TriggerDataComposite.Label.HighlightDescription" ) ); //$NON-NLS-1$

		// Composite for highlight value
		cmpVisiblity = new Composite( grpValue, SWT.NONE );
		cmpVisiblity.setLayout( new GridLayout( ) );

		addDescriptionLabel( cmpVisiblity,
				1,
				Messages.getString( "TriggerDataComposite.Label.VisiblityDescription" ) ); //$NON-NLS-1$

		// Composite for script value
		cmpScript = new Composite( grpValue, SWT.NONE );
		cmpScript.setLayout( new GridLayout( 2, false ) );

		Label lblScript = new Label( cmpScript, SWT.NONE );
		GridData gdLBLScript = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
		lblScript.setLayoutData( gdLBLScript );
		lblScript.setText( Messages.getString( "TriggerDataComposite.Lbl.Script" ) ); //$NON-NLS-1$

		txtScript = new Text( cmpScript, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL );
		txtScript.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		txtScript.setToolTipText( Messages.getString( "TriggerDataComposite.Tooltip.InputScript" ) ); //$NON-NLS-1$

		// Label lblSeries = new Label( cmpSeries, SWT.NONE );
		// GridData gdLBLSeries = new GridData( );
		// gdLBLSeries.horizontalIndent = 2;
		// lblSeries.setLayoutData( gdLBLSeries );
		// lblSeries.setText( Messages.getString(
		// "TriggerDataComposite.Lbl.SeriesDefinition" ) ); //$NON-NLS-1$
		//
		// txtSeriesDefinition = new Text( cmpSeries, SWT.BORDER );
		// GridData gdTXTSeriesDefinition = new GridData(
		// GridData.FILL_HORIZONTAL );
		// gdTXTSeriesDefinition.horizontalSpan = 2;
		// txtSeriesDefinition.setLayoutData( gdTXTSeriesDefinition );

		// Composite for tooltip value
		cmpTooltip = new Composite( grpValue, SWT.NONE );
		cmpTooltip.setLayout( new GridLayout( 2, false ) );

		Label lblDelay = new Label( cmpTooltip, SWT.NONE );
		GridData gdLBLDelay = new GridData( );
		lblDelay.setLayoutData( gdLBLDelay );
		lblDelay.setText( Messages.getString( "TriggerDataComposite.Lbl.TooltipDelay" ) ); //$NON-NLS-1$

		iscDelay = new IntegerSpinControl( cmpTooltip, SWT.NONE, 200 );
		GridData gdISCDelay = new GridData( );
		gdISCDelay.widthHint = 50;
		iscDelay.setLayoutData( gdISCDelay );
		iscDelay.setMinimum( 100 );
		iscDelay.setMaximum( 5000 );
		iscDelay.setIncrement( 100 );

		Label lblText = new Label( cmpTooltip, SWT.NONE );
		GridData gdLBLText = new GridData( );
		gdLBLText.horizontalSpan = 2;
		lblText.setLayoutData( gdLBLText );
		lblText.setText( Messages.getString( "TriggerDataComposite.Lbl.TooltipText" ) ); //$NON-NLS-1$

		if ( bEnableShowTooltipValue )
		{
			txtTooltipText = new Text( cmpTooltip, SWT.BORDER
					| SWT.MULTI | SWT.V_SCROLL );
			GridData gdTXTTooltipText = new GridData( GridData.FILL_BOTH );
			gdTXTTooltipText.horizontalSpan = 2;
			txtTooltipText.setLayoutData( gdTXTTooltipText );
		}
		else
		{
			lblText.setEnabled( false );

			addDescriptionLabel( cmpTooltip,
					2,
					Messages.getString( "TriggerDataComposite.Label.TooltipUsingDataLabelOfSeries" ) ).setEnabled( false ); //$NON-NLS-1$
		}

		cmpURL = new Composite( grpValue, SWT.NONE );
		cmpURL.setLayout( glURL );

		Label lblBaseURL = new Label( cmpURL, SWT.NONE );
		GridData gdLBLBaseURL = new GridData( );
		gdLBLBaseURL.horizontalIndent = 2;
		lblBaseURL.setLayoutData( gdLBLBaseURL );
		lblBaseURL.setText( Messages.getString( "TriggerDataComposite.Lbl.BaseURL" ) ); //$NON-NLS-1$

		// txtBaseURL = new Text( cmpURL, SWT.BORDER );
		// GridData gdTXTBaseURL = new GridData( GridData.FILL_HORIZONTAL );
		// txtBaseURL.setLayoutData( gdTXTBaseURL );
		// txtBaseURL.setEditable( false );

		btnBaseURL = new Button( cmpURL, SWT.NONE );
		{
			GridData gd = new GridData( );
			gd.horizontalSpan = 2;
			btnBaseURL.setLayoutData( gd );
			btnBaseURL.setText( Messages.getString( "TriggerDataComposite.Text.EditBaseURL" ) ); //$NON-NLS-1$
			btnBaseURL.setToolTipText( Messages.getString( "TriggerDataComposite.Tooltip.InvokeURLBuilder" ) ); //$NON-NLS-1$
			btnBaseURL.addSelectionListener( this );
		}

		Label lblDefine = new Label( cmpURL, SWT.WRAP );
		{
			GridData gd = new GridData( );
			gd.horizontalIndent = 2;
			gd.horizontalSpan = 3;
			gd.widthHint = 200;
			lblDefine.setLayoutData( gd );
			lblDefine.setText( Messages.getString( "TriggerDataComposite.Label.DefineTheParametersNames" ) ); //$NON-NLS-1$
			lblDefine.setEnabled( bEnableURLParameters );
		}

		// Label lblTarget = new Label( cmpURL, SWT.NONE );
		// GridData gdLBLTarget = new GridData( );
		// gdLBLTarget.horizontalIndent = 2;
		// lblTarget.setLayoutData( gdLBLTarget );
		// lblTarget.setText( Messages.getString(
		// "TriggerDataComposite.Lbl.Target" ) ); //$NON-NLS-1$
		//
		// txtTarget = new Text( cmpURL, SWT.BORDER );
		// GridData gdTXTTarget = new GridData( GridData.FILL_HORIZONTAL );
		// gdTXTTarget.horizontalSpan = 2;
		// txtTarget.setLayoutData( gdTXTTarget );

		grpParameters = new Group( cmpURL, SWT.NONE );
		GridData gdGRPParameters = new GridData( GridData.FILL_HORIZONTAL );
		gdGRPParameters.horizontalSpan = 3;
		grpParameters.setLayoutData( gdGRPParameters );
		grpParameters.setLayout( glParameter );
		grpParameters.setText( Messages.getString( "TriggerDataComposite.Lbl.ParameterNames" ) ); //$NON-NLS-1$
		grpParameters.setEnabled( bEnableURLParameters );

		Label lblParameters = new Label( grpParameters, SWT.WRAP );
		{
			GridData gd = new GridData( );
			gd.horizontalIndent = 2;
			gd.horizontalSpan = 3;
			gd.widthHint = 200;
			lblParameters.setLayoutData( gd );
			lblParameters.setText( Messages.getString( "TriggerDataComposite.Label.GiveANameForParameters" ) ); //$NON-NLS-1$
			lblParameters.setEnabled( bEnableURLParameters );
		}

		Label lblBaseParm = new Label( grpParameters, SWT.NONE );
		{
			GridData gdLBLBaseParm = new GridData( );
			gdLBLBaseParm.horizontalIndent = 2;
			lblBaseParm.setLayoutData( gdLBLBaseParm );
			lblBaseParm.setText( Messages.getString( "TriggerDataComposite.Lbl.CategorySeries" ) ); //$NON-NLS-1$
			lblBaseParm.setToolTipText( Messages.getString( "TriggerDataComposite.Tooltip.ParameterCategory" ) ); //$NON-NLS-1$
			lblBaseParm.setEnabled( bEnableURLParameters );
		}

		txtBaseParm = new Text( grpParameters, SWT.BORDER );
		GridData gdTXTBaseParm = new GridData( GridData.FILL_HORIZONTAL );
		gdTXTBaseParm.horizontalSpan = 2;
		txtBaseParm.setLayoutData( gdTXTBaseParm );
		txtBaseParm.setToolTipText( Messages.getString( "TriggerDataComposite.Tooltip.ParameterCategory" ) ); //$NON-NLS-1$
		txtBaseParm.setEnabled( bEnableURLParameters );

		Label lblValueParm = new Label( grpParameters, SWT.NONE );
		{
			GridData gdLBLValueParm = new GridData( );
			gdLBLValueParm.horizontalIndent = 2;
			lblValueParm.setLayoutData( gdLBLValueParm );
			lblValueParm.setText( Messages.getString( "TriggerDataComposite.Lbl.ValueSeries" ) ); //$NON-NLS-1$
			lblValueParm.setToolTipText( Messages.getString( "TriggerDataComposite.Tooltip.ParameterValue" ) ); //$NON-NLS-1$
			lblValueParm.setEnabled( bEnableURLParameters );
		}

		txtValueParm = new Text( grpParameters, SWT.BORDER );
		GridData gdTXTValueParm = new GridData( GridData.FILL_HORIZONTAL );
		gdTXTValueParm.horizontalSpan = 2;
		txtValueParm.setLayoutData( gdTXTValueParm );
		txtValueParm.setToolTipText( Messages.getString( "TriggerDataComposite.Tooltip.ParameterValue" ) ); //$NON-NLS-1$
		txtValueParm.setEnabled( bEnableURLParameters );

		Label lblSeriesParm = new Label( grpParameters, SWT.NONE );
		{
			GridData gdLBLSeriesParm = new GridData( );
			gdLBLSeriesParm.horizontalIndent = 2;
			lblSeriesParm.setLayoutData( gdLBLSeriesParm );
			lblSeriesParm.setText( Messages.getString( "TriggerDataComposite.Lbl.ValueSeriesName" ) ); //$NON-NLS-1$
			lblSeriesParm.setToolTipText( Messages.getString( "TriggerDataComposite.Tooltip.ParameterSeries" ) ); //$NON-NLS-1$
			lblSeriesParm.setEnabled( bEnableURLParameters );
		}

		txtSeriesParm = new Text( grpParameters, SWT.BORDER );
		GridData gdTXTSeriesParm = new GridData( GridData.FILL_HORIZONTAL );
		gdTXTSeriesParm.horizontalSpan = 2;
		txtSeriesParm.setLayoutData( gdTXTSeriesParm );
		txtSeriesParm.setToolTipText( Messages.getString( "TriggerDataComposite.Tooltip.ParameterSeries" ) ); //$NON-NLS-1$
		txtSeriesParm.setEnabled( bEnableURLParameters );

		populateLists( );
		slValues.topControl = cmpURL;
	}

	private void populateLists( )
	{
		cmbTriggerType.setItems( LiteralHelper.triggerConditionSet.getDisplayNames( ) );
		cmbTriggerType.select( 0 );

		cmbActionType.setItems( LiteralHelper.actionTypeSet.getDisplayNames( ) );
		cmbActionType.select( 0 );
	}

	private Label addDescriptionLabel( Composite parent, int horizontalSpan,
			String description )
	{
		Label label = new Label( parent, SWT.WRAP );
		{
			GridData gd = new GridData( );
			gd.widthHint = 200;
			gd.horizontalSpan = horizontalSpan;
			label.setLayoutData( gd );
			label.setText( description );
		}
		return label;
	}

	public void setTrigger( Trigger trigger )
	{
		updateUI( trigger );
	}

	private void updateUI( Trigger trigger )
	{
		if ( trigger == null )
		{
			clear( );
			return;
		}
		cmbTriggerType.setText( LiteralHelper.triggerConditionSet.getDisplayNameByName( trigger.getCondition( )
				.getName( ) ) );
		cmbActionType.setText( LiteralHelper.actionTypeSet.getDisplayNameByName( trigger.getAction( )
				.getType( )
				.getName( ) ) );
		switch ( cmbActionType.getSelectionIndex( ) )
		{
			case 0 :
				this.slValues.topControl = cmpURL;
				URLValue urlValue = (URLValue) trigger.getAction( ).getValue( );
				sBaseURL = ( urlValue.getBaseUrl( ).length( ) > 0 )
						? urlValue.getBaseUrl( ) : ""; //$NON-NLS-1$
				// txtBaseURL.setText( sBaseURL );
				// txtTarget.setText( ( urlValue.getTarget( ).length( ) > 0 )
				// ? urlValue.getTarget( ) : "" ); //$NON-NLS-1$
				txtBaseParm.setText( ( urlValue.getBaseParameterName( )
						.length( ) > 0 ) ? urlValue.getBaseParameterName( )
						: "" ); //$NON-NLS-1$
				txtValueParm.setText( ( urlValue.getValueParameterName( )
						.length( ) > 0 ) ? urlValue.getValueParameterName( )
						: "" ); //$NON-NLS-1$
				txtSeriesParm.setText( ( urlValue.getSeriesParameterName( )
						.length( ) > 0 ) ? urlValue.getSeriesParameterName( )
						: "" ); //$NON-NLS-1$
				break;
			case 1 :
				this.slValues.topControl = cmpTooltip;
				TooltipValue tooltipValue = (TooltipValue) trigger.getAction( )
						.getValue( );
				iscDelay.setValue( tooltipValue.getDelay( ) );
				if ( bEnableShowTooltipValue )
				{
					txtTooltipText.setText( ( tooltipValue.getText( ).length( ) > 0 )
							? tooltipValue.getText( ) : "" ); //$NON-NLS-1$
				}
				break;
			case 2 :
				this.slValues.topControl = cmpVisiblity;
				// SeriesValue seriesValue = (SeriesValue) trigger.getAction( )
				// .getValue( );
				// txtSeriesDefinition.setText( ( seriesValue.getName( ).length(
				// ) > 0 ) ? seriesValue.getName( )
				// : "" ); //$NON-NLS-1$
				break;
			case 3 :
				this.slValues.topControl = cmpScript;
				ScriptValue scriptValue = (ScriptValue) trigger.getAction( )
						.getValue( );
				txtScript.setText( ( scriptValue.getScript( ).length( ) > 0 )
						? scriptValue.getScript( ) : "" ); //$NON-NLS-1$
				break;
			case 4 :
				this.slValues.topControl = cmpHighlight;
				// SeriesValue highlightSeriesValue = (SeriesValue)
				// trigger.getAction( )
				// .getValue( );
				// txtSeriesDefinition.setText( ( highlightSeriesValue.getName(
				// )
				// .length( ) > 0 ) ? highlightSeriesValue.getName( ) : "" );
				// //$NON-NLS-1$
				break;
			case 5 :
				this.slValues.topControl = cmpCallback;
				break;
			default :
				this.slValues.topControl = cmpDefault;
				break;
		}
		grpValue.layout( );
	}

	public Trigger getTrigger( )
	{
		ActionValue value = null;
		switch ( cmbActionType.getSelectionIndex( ) )
		{
			case 0 :
				value = URLValueImpl.create( sBaseURL,
						null,// txtTarget.getText( ),
						txtBaseParm.getText( ),
						txtValueParm.getText( ),
						txtSeriesParm.getText( ) );
				break;
			case 1 :
				value = TooltipValueImpl.create( iscDelay.getValue( ), "" ); //$NON-NLS-1$
				if ( bEnableShowTooltipValue )
				{
					( (TooltipValue) value ).setText( txtTooltipText.getText( ) );
				}
				break;
			case 2 :
				value = AttributeFactory.eINSTANCE.createSeriesValue( );
				( (SeriesValue) value ).setName( "" ); //$NON-NLS-1$
				break;
			case 3 :
				value = AttributeFactory.eINSTANCE.createScriptValue( );
				( (ScriptValue) value ).setScript( txtScript.getText( ) );
				break;
			case 4 :
				value = AttributeFactory.eINSTANCE.createSeriesValue( );
				( (SeriesValue) value ).setName( "" ); //$NON-NLS-1$
				break;
			default :
				break;
		}
		Action action = ActionImpl.create( ActionType.get( LiteralHelper.actionTypeSet.getNameByDisplayName( cmbActionType.getText( ) ) ),
				value );
		return TriggerImpl.create( TriggerCondition.get( LiteralHelper.triggerConditionSet.getNameByDisplayName( cmbTriggerType.getText( ) ) ),
				action );
	}

	public void clear( )
	{
		cmbTriggerType.select( 0 );
		cmbActionType.select( 0 );
		switch ( cmbActionType.getSelectionIndex( ) )
		{
			case 0 :
				this.slValues.topControl = cmpURL;
				break;
			case 1 :
				this.slValues.topControl = cmpTooltip;
				break;
			case 2 :
				this.slValues.topControl = cmpVisiblity;
				break;
			case 3 :
				this.slValues.topControl = cmpScript;
				break;
			case 4 :
				this.slValues.topControl = cmpHighlight;
				break;
			case 5 :
				this.slValues.topControl = cmpCallback;
				break;
			default :
				this.slValues.topControl = cmpDefault;
				break;
		}
		grpValue.layout( );
	}

	public Point getPreferredSize( )
	{
		return new Point( 260, 260 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected( SelectionEvent e )
	{
		if ( e.getSource( ).equals( cmbActionType ) )
		{
			switch ( cmbActionType.getSelectionIndex( ) )
			{
				case 0 :
					this.slValues.topControl = cmpURL;
					break;
				case 1 :
					this.slValues.topControl = cmpTooltip;
					break;
				case 2 :
					this.slValues.topControl = cmpVisiblity;
					break;
				case 3 :
					this.slValues.topControl = cmpScript;
					break;
				case 4 :
					this.slValues.topControl = cmpHighlight;
					break;
				case 5 :
					this.slValues.topControl = cmpCallback;
					break;
				default :
					this.slValues.topControl = cmpDefault;
					break;
			}
			grpValue.layout( );
		}
		else if ( e.getSource( ).equals( btnBaseURL ) )
		{
			try
			{
				if ( wizardContext != null )
				{
					sBaseURL = wizardContext.getUIServiceProvider( )
							.invoke( IUIServiceProvider.COMMAND_HYPERLINK,
									sBaseURL,
									wizardContext.getExtendedItem( ),
									null );
				}
			}
			catch ( ChartException ex )
			{
				ChartWizard.displayException( ex );
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected( SelectionEvent e )
	{
	}
}