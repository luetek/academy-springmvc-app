import Cherry from 'cherry-markdown';
const echarts = require('echarts');

import 'cherry-markdown/dist/cherry-markdown.css';
import '../scss/app.scss';

console.log("This is called");
//https://jefago.github.io/tiny-markdown-editor/
export function useMarkdownEditor(editorElement, commandBarElement) {

	console.log("Init MD Editor");
	const etr = new Cherry({
	  id: editorElement,
	  value: '# welcome to cherry editor!',
	  locale:'en_US',
	});
	return etr;
}

function setMarkdown(editor) {
    document.querySelector('#markdownContentField').value = editor.getMarkdown();
    console.log("set Markdown");
}
window.echarts = echarts;
window.setMarkdown = setMarkdown;
window.useMarkdownEditor = useMarkdownEditor;