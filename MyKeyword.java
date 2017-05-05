
/**
 * 
 * @author C
 * @brief tools of Category Names, Replace Words and Filtering Words
 */
public interface MyKeyword {
	final static String[] categoryNames = {	
			"�ǰ��� ����", "����", "����", "����", "��ȭ�� ����", "��ȸ", "���", "������Ȱ"
	};
	
	// replace these characters to ' ' 
	final static char[] replaceWords = {
			'-', ',', '/', '.', '<', '>', '\"', ',', '[', ']', '{', '}',  
			'|', '!', '#', '%', '&', '\'', '=', '	', ':', ';', '?', '��', '��',
			'`', '*', '+', '-', '(', ')','��', '��','��', '��','$'
	};
	
	//if the word length  == 1, Skip
	final static String alphabets = 
			"abcdefghijklmnopqrstuovwxyz"
	;
	
	// if the word is consisted of number, Skip
	final static String numbers = 
			"0123456789"
	;

	final static String DOCUMENT = "@DOCUMENT";
	final static String CATEGORY = "#CAT'03:";
	final static String TEXT	 = "#TEXT ";
	final static String KEYWORD  = "<KW>";
}
