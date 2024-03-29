USE [master]
GO
/****** Object:  Database [J3LP0002]    Script Date: 12/12/2020 9:37:30 PM ******/
CREATE DATABASE [J3LP0002]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'J3LP0002', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\J3LP0002.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'J3LP0002_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\J3LP0002_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [J3LP0002] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [J3LP0002].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [J3LP0002] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [J3LP0002] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [J3LP0002] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [J3LP0002] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [J3LP0002] SET ARITHABORT OFF 
GO
ALTER DATABASE [J3LP0002] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [J3LP0002] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [J3LP0002] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [J3LP0002] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [J3LP0002] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [J3LP0002] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [J3LP0002] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [J3LP0002] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [J3LP0002] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [J3LP0002] SET  DISABLE_BROKER 
GO
ALTER DATABASE [J3LP0002] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [J3LP0002] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [J3LP0002] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [J3LP0002] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [J3LP0002] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [J3LP0002] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [J3LP0002] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [J3LP0002] SET RECOVERY FULL 
GO
ALTER DATABASE [J3LP0002] SET  MULTI_USER 
GO
ALTER DATABASE [J3LP0002] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [J3LP0002] SET DB_CHAINING OFF 
GO
ALTER DATABASE [J3LP0002] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [J3LP0002] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [J3LP0002] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'J3LP0002', N'ON'
GO
USE [J3LP0002]
GO
/****** Object:  Table [dbo].[tblAccount]    Script Date: 12/12/2020 9:37:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblAccount](
	[userID] [varchar](30) NOT NULL,
	[password] [varchar](20) NULL,
	[name] [nvarchar](30) NULL,
	[roleID] [varchar](5) NULL,
	[status] [varchar](10) NULL,
 CONSTRAINT [PK_account] PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblBook]    Script Date: 12/12/2020 9:37:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblBook](
	[bookID] [nvarchar](20) NOT NULL,
	[title] [nvarchar](50) NOT NULL,
	[image] [nvarchar](50) NULL,
	[description] [nvarchar](30) NULL,
	[price] [float] NULL,
	[author] [nvarchar](30) NULL,
	[categoryID] [varchar](10) NULL,
	[quantity] [int] NULL,
	[importDate] [date] NULL,
	[status] [varchar](10) NULL,
 CONSTRAINT [PK_book] PRIMARY KEY CLUSTERED 
(
	[bookID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblCategory]    Script Date: 12/12/2020 9:37:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblCategory](
	[categoryID] [varchar](10) NOT NULL,
	[categoryName] [nvarchar](50) NULL,
 CONSTRAINT [PK_tblCategory] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblDiscountCode]    Script Date: 12/12/2020 9:37:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblDiscountCode](
	[discountID] [varchar](10) NOT NULL,
	[discountPercent] [int] NULL,
	[date] [date] NULL,
 CONSTRAINT [PK_TblDiscountCode] PRIMARY KEY CLUSTERED 
(
	[discountID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblOrder]    Script Date: 12/12/2020 9:37:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblOrder](
	[orderID] [varchar](10) NOT NULL,
	[totalPrice] [float] NULL,
	[userID] [varchar](30) NULL,
	[date] [datetime] NULL,
	[discountID] [varchar](10) NULL,
 CONSTRAINT [PK_TblBooking] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblOrderDetail]    Script Date: 12/12/2020 9:37:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblOrderDetail](
	[orderID] [varchar](10) NOT NULL,
	[bookID] [nvarchar](20) NOT NULL,
	[bookTitle] [nvarchar](50) NULL,
	[quantity] [int] NULL,
	[price] [float] NULL,
 CONSTRAINT [PK_TblOrderDetail] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC,
	[bookID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRole]    Script Date: 12/12/2020 9:37:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRole](
	[roleID] [varchar](5) NOT NULL,
	[roleName] [varchar](20) NULL,
 CONSTRAINT [PK_tblRole] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblAccount] ([userID], [password], [name], [roleID], [status]) VALUES (N'phuc      ', N'123456    ', N'Phúc', N'US', N'Active    ')
INSERT [dbo].[tblAccount] ([userID], [password], [name], [roleID], [status]) VALUES (N'sa', N'123456', N'Chọp Chẹp', N'AD', N'Active')
INSERT [dbo].[tblBook] ([bookID], [title], [image], [description], [price], [author], [categoryID], [quantity], [importDate], [status]) VALUES (N'English2', N'English2', N'english2.jpg', N'sach giao khoa english 2', 30, N'Hoang Anh', N'HD', 13, CAST(N'2020-12-12' AS Date), N'Active')
INSERT [dbo].[tblBook] ([bookID], [title], [image], [description], [price], [author], [categoryID], [quantity], [importDate], [status]) VALUES (N'GK6', N'Tieng Anh 6', N'english6.jpg', N'Giao khoa english 6', 50, N'Khong biet', N'GK', 29, CAST(N'2020-12-12' AS Date), N'Active')
INSERT [dbo].[tblCategory] ([categoryID], [categoryName]) VALUES (N'GK', N'Giáo Khoa')
INSERT [dbo].[tblCategory] ([categoryID], [categoryName]) VALUES (N'HD', N'Hướng Dẫn')
INSERT [dbo].[tblCategory] ([categoryID], [categoryName]) VALUES (N'LN', N'Light Novel')
INSERT [dbo].[tblDiscountCode] ([discountID], [discountPercent], [date]) VALUES (N'FORME', 50, CAST(N'2022-12-21' AS Date))
INSERT [dbo].[tblDiscountCode] ([discountID], [discountPercent], [date]) VALUES (N'FORYOU', 10, CAST(N'2021-12-21' AS Date))
INSERT [dbo].[tblDiscountCode] ([discountID], [discountPercent], [date]) VALUES (N'NONE', 0, CAST(N'2022-12-21' AS Date))
INSERT [dbo].[tblOrder] ([orderID], [totalPrice], [userID], [date], [discountID]) VALUES (N'0', 27, N'phuc', CAST(N'2020-12-12 18:18:51.553' AS DateTime), N'FORYOU')
INSERT [dbo].[tblOrder] ([orderID], [totalPrice], [userID], [date], [discountID]) VALUES (N'1', 30, N'phuc', CAST(N'2020-12-29 18:19:09.787' AS DateTime), N'NONE')
INSERT [dbo].[tblOrder] ([orderID], [totalPrice], [userID], [date], [discountID]) VALUES (N'2', 40, N'phuc', CAST(N'2020-12-12 18:20:00.853' AS DateTime), N'FORME')
INSERT [dbo].[tblOrderDetail] ([orderID], [bookID], [bookTitle], [quantity], [price]) VALUES (N'0', N'English2', N'English2', 1, 30)
INSERT [dbo].[tblOrderDetail] ([orderID], [bookID], [bookTitle], [quantity], [price]) VALUES (N'1', N'English2', N'English2', 1, 30)
INSERT [dbo].[tblOrderDetail] ([orderID], [bookID], [bookTitle], [quantity], [price]) VALUES (N'2', N'English2', N'English2', 1, 30)
INSERT [dbo].[tblOrderDetail] ([orderID], [bookID], [bookTitle], [quantity], [price]) VALUES (N'2', N'GK6', N'Tieng Anh 6', 1, 50)
INSERT [dbo].[tblRole] ([roleID], [roleName]) VALUES (N'AD', N'Admin')
INSERT [dbo].[tblRole] ([roleID], [roleName]) VALUES (N'US', N'User')
ALTER TABLE [dbo].[tblAccount]  WITH CHECK ADD  CONSTRAINT [FK_tblAccount_tblRole] FOREIGN KEY([roleID])
REFERENCES [dbo].[tblRole] ([roleID])
GO
ALTER TABLE [dbo].[tblAccount] CHECK CONSTRAINT [FK_tblAccount_tblRole]
GO
ALTER TABLE [dbo].[tblBook]  WITH CHECK ADD  CONSTRAINT [FK_tblBook_tblCategory] FOREIGN KEY([categoryID])
REFERENCES [dbo].[tblCategory] ([categoryID])
GO
ALTER TABLE [dbo].[tblBook] CHECK CONSTRAINT [FK_tblBook_tblCategory]
GO
ALTER TABLE [dbo].[tblOrder]  WITH CHECK ADD  CONSTRAINT [FK_tblOrder_tblAccount] FOREIGN KEY([userID])
REFERENCES [dbo].[tblAccount] ([userID])
GO
ALTER TABLE [dbo].[tblOrder] CHECK CONSTRAINT [FK_tblOrder_tblAccount]
GO
ALTER TABLE [dbo].[tblOrder]  WITH CHECK ADD  CONSTRAINT [FK_tblOrder_tblDiscountCode] FOREIGN KEY([discountID])
REFERENCES [dbo].[tblDiscountCode] ([discountID])
GO
ALTER TABLE [dbo].[tblOrder] CHECK CONSTRAINT [FK_tblOrder_tblDiscountCode]
GO
ALTER TABLE [dbo].[tblOrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_tblOrderDetail_tblBook] FOREIGN KEY([bookID])
REFERENCES [dbo].[tblBook] ([bookID])
GO
ALTER TABLE [dbo].[tblOrderDetail] CHECK CONSTRAINT [FK_tblOrderDetail_tblBook]
GO
ALTER TABLE [dbo].[tblOrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_tblOrderDetail_tblOrder] FOREIGN KEY([orderID])
REFERENCES [dbo].[tblOrder] ([orderID])
GO
ALTER TABLE [dbo].[tblOrderDetail] CHECK CONSTRAINT [FK_tblOrderDetail_tblOrder]
GO
USE [master]
GO
ALTER DATABASE [J3LP0002] SET  READ_WRITE 
GO
