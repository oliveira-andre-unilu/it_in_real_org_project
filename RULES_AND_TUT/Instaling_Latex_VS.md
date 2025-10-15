# LaTeX Installation Guide for Windows and macOS with VS Code

## Overview
This guide covers installing LaTeX natively on both Windows and macOS, configured to work with VS Code and the LaTeX Workshop extension.

---

## Windows Installation

### Step 1: Install MiKTeX
1. **Download MiKTeX**
   - Go to [MiKTeX download page](https://miktex.org/download)
   - Download the **64-bit MiKTeX Installer**

2. **Run the Installer**
   - Choose "Install MiKTeX for anyone who uses this computer" (requires admin rights)
   - Or "Install MiKTeX only for me" if no admin rights
   - **Important settings:**
     - Install missing packages on the fly: **Yes**
     - Preferred paper: A4 or Letter
   - Complete the installation

### Step 2: Install Strawberry Perl (Required for some LaTeX packages)
1. **Download Strawberry Perl**
   - Go to [Strawberry Perl website](https://strawberryperl.com/)
   - Download the latest version

2. **Install Strawberry Perl**
   - Run the installer
   - Use default settings
   - Restart your computer after installation (Not needed in my case)

### Step 3: Verify Installation
Open Command Prompt and test:
```cmd
pdflatex --version
perl --version
```

---

## macOS Installation

### Step 1: Install MacTeX
1. **Download MacTeX**
   - Go to [MacTeX download page](https://tug.org/mactex/)
   - Download **MacTeX.pkg** (full distribution)

2. **Install MacTeX**
   - Open the downloaded .pkg file
   - Follow the installer instructions
   - Installation may take 30-60 minutes

### Step 2: Verify Installation
Open Terminal and test:
```bash
pdflatex --version
```

---

## VS Code Setup (Both Platforms)

### Step 1: Install VS Code Extensions
1. **Install LaTeX Workshop**
   - Open VS Code
   - Go to Extensions (Ctrl+Shift+X / Cmd+Shift+X)
   - Search for "LaTeX Workshop"
        - By James Yu

2. **Optional but Recommended Extensions**
   - "LaTeX Utilities" - Additional LaTeX tools
   - "Spell Right" - Spell checking for LaTeX

---

## Testing Your Setup

### Step 1: Create a Test Document or use the `something.tex` file in this folder
Create a file called `test.tex`:

```latex
\documentclass{article}
\usepackage{amsmath}

\begin{document}

\section{Test Section}

Hello LaTeX! This is a test document.

Math test: $E = mc^2$

\[
\int_{-\infty}^{\infty} e^{-x^2} dx = \sqrt{\pi}
\]

\end{document}
```

### Step 2: Build the Document
1. **Open the .tex file** in VS Code
2. **Build with:**
   - `Ctrl+Alt+B` (Windows) or `Cmd+Alt+B` (macOS)
   - Or click the "Build LaTeX project" button in the LaTeX sidebar ("Usually a pla button on the top right corner")

### Step 3: View the PDF
- The PDF should automatically open in a tab within VS Code
- Or find it in the `out/` (or in the same) folder next to your .tex file

### Step 4: Stash all the generated files
- Please do not add any of the generated files to git. This would allow everyone to use this folder as a testing environment
---

## Troubleshooting (Copied from forums, did not actually have this issues)

### Configure VS Code Settings (VS code issues)

**Open Settings JSON:**
1. Press `Ctrl+Shift+P` (Windows) or `Cmd+Shift+P` (macOS)
2. Type "Preferences: Open Settings (JSON)"
3. Select it

**Add these settings:**

```json
{
    // LaTeX Workshop Configuration
    "latex-workshop.latex.outDir": "%DIR%/out",
    "latex-workshop.latex.autoBuild.run": "onFileChange",
    "latex-workshop.latex.recipe.default": "lastUsed",
    
    // PDF Viewer Settings
    "latex-workshop.view.pdf.viewer": "tab",
    "latex-workshop.view.pdf.zoom": "page-width",
    
    // Tool Configuration
    "latex-workshop.latex.recipes": [
        {
            "name": "pdflatex",
            "tools": ["pdflatex"]
        },
        {
            "name": "xelatex", 
            "tools": ["xelatex"]
        }
    ],
    
    "latex-workshop.latex.tools": [
        {
            "name": "pdflatex",
            "command": "pdflatex",
            "args": [
                "-synctex=1",
                "-interaction=nonstopmode",
                "-file-line-error",
                "-output-directory=%DIR%/out",
                "%DOC%"
            ]
        },
        {
            "name": "xelatex",
            "command": "xelatex", 
            "args": [
                "-synctex=1",
                "-interaction=nonstopmode",
                "-file-line-error",
                "-output-directory=%DIR%/out",
                "%DOC%"
            ]
        }
    ],
    
    // Auto Cleanup
    "latex-workshop.latex.autoClean.run": "onBuilt",
    "latex-workshop.latex.clean.fileTypes": [
        "*.aux",
        "*.bbl",
        "*.blg",
        "*.idx",
        "*.ind",
        "*.lof",
        "*.lot", 
        "*.out",
        "*.toc",
        "*.acn",
        "*.acr",
        "*.alg",
        "*.glg",
        "*.glo",
        "*.gls",
        "*.ist",
        "*.fls",
        "*.log",
        "*.fdb_latexmk",
        "*.snm",
        "*.nav"
    ]
}
```
---

### Common Windows Issues
1. **"Command not found" errors**
   - Restart VS Code after installing MiKTeX
   - Ensure MiKTeX bin directory is in PATH (usually `C:\Program Files\MiKTeX\miktex\bin\x64\`)

2. **Perl-related errors**
   - Verify Strawberry Perl installation
   - Restart computer after Perl installation

3. **Missing packages**
   - MiKTeX should prompt to install missing packages
   - Run MiKTeX Console as admin to update package database if needed

### Common macOS Issues
1. **"Command not found"**
   - Restart Terminal or VS Code
   - MacTeX usually adds to PATH automatically

2. **Font issues**
   - Use XeLaTeX if having font problems
   - Change recipe to "xelatex"

### VS Code Issues
1. **LaTeX commands not working**
   - Reload VS Code window (Ctrl+Shift+P → "Developer: Reload Window")
   - Check LaTeX Workshop extension is enabled

2. **PDF not showing**
   - Check `latex-workshop.latex.outDir` setting
   - Run "LaTeX Workshop: Clean auxiliary files" and rebuild

---

## Useful VS Code Shortcuts

- **Build LaTeX**: `Ctrl+Alt+B` (Windows) / `Cmd+Alt+B` (macOS)
- **View LaTeX PDF**: `Ctrl+Alt+V` (Windows) / `Cmd+Alt+V` (macOS)  
- **SyncTeX**: `Ctrl+Click` in PDF to jump to source code
- **Clean auxiliary files**: `Ctrl+Shift+P` → "LaTeX Workshop: Clean auxiliary files"

---

## Package Management

### Windows (MiKTeX)
- Use **MiKTeX Console** to:
  - Update package database
  - Install missing packages
  - Update installed packages

### macOS (MacTeX)
- Use **TeX Live Utility** to:
  - Manage and update packages
  - Check for updates

This setup provides a complete, native LaTeX environment on both Windows and macOS with full VS Code integration.