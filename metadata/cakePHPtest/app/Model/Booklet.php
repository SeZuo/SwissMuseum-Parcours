<?php
class Booklet extends AppModel {
	public $name = 'Booklet';
	
	public $belongsTo = array(
    	'PageData' => array(
        	'className'    => 'PageData',
        	'dependent'    => false
		)
    );
	
	public $hasAndBelongsToMany = array(
        'Chapter' =>
            array(
                'className'              => 'Chapter',
                'unique'                 => false, // the purpose of this arg is a bit unclear
            )
    );
}
?>
