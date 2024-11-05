import Cherry from 'cherry-markdown';
const echarts = require('echarts');

import 'cherry-markdown/dist/cherry-markdown.css';
import '../scss/app.scss';

console.log("This is called");
//https://jefago.github.io/tiny-markdown-editor/
export function useMarkdownEditor(editorElement, inputElement ) {

	console.log("Init MD Editor");
	const etr = new Cherry({
	  id: editorElement,
	  value: inputElement.value,
	  locale:'en_US',
	});
	return etr;
}

window.echarts = echarts;
window.useMarkdownEditor = useMarkdownEditor;