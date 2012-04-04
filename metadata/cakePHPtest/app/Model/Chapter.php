<?php
class Chapter extends AppModel {
	public $name = 'Chapter';
	
	public $belongsTo = array(
    	'PageData' => array(
        	'className'    => 'PageData',
        	'dependent'    => false
		)
    );
	
	public $hasAndBelongsToMany = array(
        'Booklet' =>
            array(
                'className'              => 'Booklet',
                'unique'                 => false, // the purpose of this arg is a bit unclear
            ),
        'Page' =>
            array(
                'className'              => 'Page',
                'unique'                 => false, // the purpose of this arg is a bit unclear
            )
    );
}
?>
