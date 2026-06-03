const API_BASE = '/api';

async function request(url, options = {}) {
    const defaultOptions = {
        headers: { 'Content-Type': 'application/json' }
    };
    const response = await fetch(API_BASE + url, { ...defaultOptions, ...options });
    const data = await response.json();
    if (data.code === 401) {
        window.location.href = '/login.html';
        return null;
    }
    return data;
}

function showToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);
    setTimeout(() => toast.remove(), 3000);
}

function showModal(modalId) {
    document.getElementById(modalId).classList.add('show');
}

function hideModal(modalId) {
    document.getElementById(modalId).classList.remove('show');
}

function formatDate(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    return date.toLocaleDateString('zh-CN') + ' ' + date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' });
}

function getStatusBadge(status, dueTime) {
    if (status === 1) return '<span class="badge badge-success">已归还</span>';
    if (status === 2 || (status === 0 && new Date(dueTime) < new Date())) return '<span class="badge badge-danger">已超期</span>';
    return '<span class="badge badge-info">借阅中</span>';
}

async function logout() {
    await request('/auth/logout', { method: 'POST' });
    window.location.href = '/login.html';
}
