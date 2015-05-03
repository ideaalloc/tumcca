/*
    *
    * Wijmo Library 5.20143.39
    * http://wijmo.com/
    *
    * Copyright(c) GrapeCity, Inc.  All rights reserved.
    * 
    * Licensed under the Wijmo Commercial License. 
    * sales@wijmo.com
    * http://wijmo.com/products/wijmo-5/license/
    *
    */
/**
* Defines the @see:FlexGridFilter and associated classes.
*
* The @see:FlexGridFilter class is an extension that adds column
* filtering to @see:FlexGrid controls.
*
* This extension depends on the <b>wijmo.grid</b> and <b>wijmo.input</b>
* modules.
*
* The example below shows how you can add a @see:FlexGridFilter to
* a @see:FlexGrid control.
*
* @fiddle:6aax531a
*/
declare module wijmo.grid.filter {
    /**
    * Implements an Excel-style filter for @see:FlexGrid controls.
    *
    * To enable filtering on a @see:FlexGrid control, create an instance
    * of the @see:FlexGridFilter and pass the grid as a parameter to the
    * constructor. For example:
    *
    * <pre>
    * // create FlexGrid
    * var flex = new wijmo.grid.FlexGrid('#gridElement');
    * // enable filtering on the FlexGrid
    * var filter = new wijmo.grid.filter.FlexGridFilter(flex);
    * </pre>
    *
    * Once this is done, a filter icon is added to the grid's column headers.
    * Clicking the icon shows an editor where the user can edit the filter
    * conditions for that column.
    *
    * The @see:FlexGridFilter class depends on the <b>wijmo.grid</b> and
    * <b>wijmo.input</b> modules.
    */
    class FlexGridFilter {
        static _WJA_FILTER: string;
        public _grid: FlexGrid;
        public _filters: ColumnFilter[];
        public _filterColumns: string[];
        public _divEdt: HTMLElement;
        public _edtCol: Column;
        public _ehBoundsChanged: any;
        public _ehWheel: any;
        public _showIcons: boolean;
        /**
        * Initializes a new instance of the @see:FlexGridFilter.
        *
        * @param grid The @see:FlexGrid to filter.
        */
        constructor(grid: FlexGrid);
        /**
        * Gets or sets a value indicating whether the @see:FlexGridFilter adds filter
        * editing buttons to the grid's column headers.
        */
        public showFilterIcons : boolean;
        /**
        * Gets or sets an array containing the names or bindings of the columns
        * that have filters.
        *
        * Setting this property to null or to an empty array adds filters to all
        * columns.
        */
        public filterColumns : string[];
        /**
        * Gets the filter for the given column.
        *
        * @param col The @see:Column that the filter applies to.
        * @param create The value indicating whether to create the filter if it does not exist.
        */
        public getColumnFilter(col: Column, create?: boolean): ColumnFilter;
        /**
        * Shows the filter editor for the given grid column.
        *
        * @param col The @see:Column that contains the filter to edit.
        */
        public editColumnFilter(col: any): void;
        /**
        * Closes the filter editor.
        */
        public closeEditor(): void;
        /**
        * Applies the current column filters to the grid.
        */
        public apply(): void;
        /**
        * Clears all column filters.
        */
        public clear(): void;
        /**
        * Occurs after the filter is applied.
        */
        public filterApplied: Event;
        /**
        * Raises the @see:filterApplied event.
        */
        public onFilterApplied(): void;
        public _filter(item: any): boolean;
        public _formatItem(sender: FlexGrid, e: FormatItemEventArgs): void;
        public _mouseDown(e: any): void;
        public _hasAttribute(e: any, att: string): boolean;
        public _updatePosition(): void;
        public _boundsChanged(e: any): void;
        public _wheel(e: any): void;
    }
}

declare module wijmo.grid.filter {
    /**
    * Defines a filter for a column on a @see:FlexGrid control.
    *
    * Column filters contain two conditions that may be combined
    * using an 'and' or an 'or' operator.
    *
    * This class is used by the @see:FlexGridFilter class; you
    * rarely use it directly.
    */
    class ColumnFilter {
        public _col: Column;
        public _bnd: Binding;
        public _c1: FilterCondition;
        public _c2: FilterCondition;
        public _and: boolean;
        /**
        * Initializes a new instance of a @see:ColumnFilter.
        *
        * @param column The column to filter.
        */
        constructor(column: Column);
        /**
        * Gets the @see:Column to filter.
        */
        public column : Column;
        /**
        * Gets the first condition in the filter.
        */
        public condition1 : FilterCondition;
        /**
        * Gets the second condition in the filter.
        */
        public condition2 : FilterCondition;
        /**
        * Gets a value indicating whether to combine the two conditions
        * with an AND or an OR operator.
        */
        public and : boolean;
        /**
        * Gets a value indicating whether the filter is active.
        *
        * The filter is active if at least one of the two conditions
        * has its operator set to a non-null value.
        */
        public isActive : boolean;
        /**
        * Returns a value indicating whether the given value passes this @see:Filter.
        *
        * @param value The value to test.
        */
        public apply(value: any): boolean;
    }
}

declare module wijmo.grid.filter {
    /**
    * The editor used to inspect and modify @see:ColumnFilter objects.
    *
    * This class is used by the @see:FlexGridFilter class; you
    * rarely use it directly.
    */
    class ColumnFilterEditor extends Control {
        public _filter: ColumnFilter;
        public _cmb1: input.ComboBox;
        public _val1: any;
        public _cmb2: input.ComboBox;
        public _val2: any;
        public _divHdr: HTMLElement;
        public _divCmb1: HTMLElement;
        public _divVal1: HTMLElement;
        public _divCmb2: HTMLElement;
        public _divVal2: HTMLElement;
        public _spAnd: HTMLSpanElement;
        public _spOr: HTMLSpanElement;
        public _btnAnd: HTMLInputElement;
        public _btnOr: HTMLInputElement;
        public _btnApply: HTMLLinkElement;
        public _btnClear: HTMLLinkElement;
        /**
        * Gets or sets the template used to instantiate @see:ColumnFilterEditor controls.
        */
        static controlTemplate: string;
        public '</div>': any;
        /**
        * Initializes a new instance of the @see:ColumnFilterEditor.
        *
        * @param element The DOM element that hosts the control, or a selector
        * for the host element (e.g. '#theCtrl').
        * @param filter The @see:ColumnFilter to edit.
        */
        constructor(element: any, filter: ColumnFilter);
        /**
        * Gets a reference to the @see:ColumnFilter being edited.
        */
        public filter : ColumnFilter;
        /**
        * Occurs after the filter is modified.
        */
        public filterChanged: Event;
        /**
        * Raises the @see:filterChanged event.
        */
        public onFilterChanged(e?: EventArgs): void;
        public _updateUIFromFilter(): void;
        public _updateFilterFromUI(): void;
        public _createOperatorCombo(element: any): input.ComboBox;
        public _createValueInput(element: any): Control;
        public _isTimeFormat(fmt: string): boolean;
        public _btnAndOrChanged(e: any): void;
        public _btnApplyClearClicked(e: any): void;
    }
}

declare module wijmo.grid.filter {
    /**
    * Defines a filter condition.
    *
    * This class is used by the @see:FlexGridFilter class; you
    * rarely use it directly.
    */
    class FilterCondition {
        public _op: Operator;
        public _val: any;
        public _strVal: string;
        /**
        * Gets or sets the operator used by this @see:Condition.
        */
        public operator : Operator;
        /**
        * Gets or sets the value used by this @see:Condition.
        */
        public value : any;
        /**
        * Returns a value that determines whether the given value passes this @see:Condition.
        *
        * @param value The value to test.
        */
        public apply(value: any): boolean;
    }
    /**
    * Specifies filter condition operators.
    */
    enum Operator {
        /** Equals. */
        EQ = 0,
        /** Does not equal. */
        NE = 1,
        /** Greater than. */
        GT = 2,
        /** Greater than or equal to. */
        GE = 3,
        /** Less than. */
        LT = 4,
        /** Less than or equal to. */
        LE = 5,
        /** Begins with. */
        BW = 6,
        /** Ends with. */
        EW = 7,
        /** Contains. */
        CT = 8,
        /** Does not contain. */
        NC = 9,
    }
}

