import type React from "react"
import type { Metadata } from "next"
import { Inter } from "next/font/google"
import "./globals.css"
import Navbar from "@/components/Navbar"

const inter = Inter({ subsets: ["latin"] })

export const metadata: Metadata = {
  title: "Modern Store - Cửa hàng thời trang hiện đại",
  description: "Khám phá bộ sưu tập thời trang hiện đại với thiết kế tối giản",
    generator: 'v0.dev'
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="vi">
      <body className={inter.className}>
        <Navbar />
        <main className="pt-16">{children}</main>
      </body>
    </html>
  )
}
