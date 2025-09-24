import DOMPurify from 'dompurify'

/**
 * 安全净化富文本 HTML
 * @param {string} html 原始 HTML 字符串
 * @returns {string} 经过净化的安全字符串
 */
export function sanitize(html) {
  if (!html || typeof html !== 'string') return ''
  return DOMPurify.sanitize(html, {
    USE_PROFILES: { html: true }
  })
}
